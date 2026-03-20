import json

import pymongo
import pytz
import bsonjs
import os
import threading
from pymongo import MongoClient, InsertOne, UpdateOne
import datetime
from bson.raw_bson import RawBSONDocument
from pymongo.errors import BulkWriteError

date_pattern = "%Y-%m-%dT%H:%M:%S.%f"
strMongoIn = ""
strMongoOut = ""
coll_name_new = ""
coll_name_old = ""
bulk_size = 0
limit = 0
total = 0
thread_number = 0
execution_number = 0
vendor_id = ""

init_time = datetime.datetime.now(pytz.utc)
str_date = datetime.datetime.now(pytz.UTC).strftime(date_pattern)[:-3]

query = {}


def find_promotions_v1(skip, limit):
    global strMongoIn, date_pattern, str_date, coll_name_old, query

    print("Buscando documentos de " + str(skip) + " a " + str(skip + limit))

    client = MongoClient(strMongoIn)

    db = client.Promotion
    collection = db[coll_name_old]

    cursor = collection.find(query).skip(skip).limit(limit)

    client.close

    return cursor


def run_bulk_update(promotions):
    global init_time, strMongoOut, coll_name_new

    total_seconds = (datetime.datetime.now(pytz.utc) - init_time).total_seconds()
    print(str(total_seconds) + "s para iniciar o import para base de " + str(len(promotions)) + " registros")

    client = MongoClient(strMongoOut, serverSelectionTimeoutMS=120000)

    db = client.Promotion
    collection = db[coll_name_new]
    requesting = []

    for json_obj in promotions:
        if json_obj is None: continue
        raw_bson_id = bsonjs.loads(json.dumps({"_id": json_obj["_id"]}))
        bson_id = RawBSONDocument(raw_bson_id)
        raw_bson_platformid = bsonjs.loads(json.dumps({'$set': {'promotionPlatformId': json_obj["promotionPlatformId"]}}))
        bson_platformid = RawBSONDocument(raw_bson_platformid)
        requesting.append(UpdateOne(bson_id, bson_platformid))


    try:
        collection.bulk_write(requesting, False)
        total_seconds = (datetime.datetime.now(pytz.utc) - init_time).total_seconds()
        print(str(total_seconds) + "s para o bulk write na collection:" + str(coll_name_new))
    except BulkWriteError as error:
        print(str(error.details['writeErrors'][0]['errmsg']))
        pass

    client.close

def save_promotion_v2(promotion_v1, promotions_platform_id):
    global bulk_size

    promotions_platform_id.append(create_promotion_platform_id(promotion_v1))


    if len(promotions_platform_id) == bulk_size:
        run_bulk_update(promotions_platform_id)
        promotions_platform_id = []

    return promotions_platform_id

def promotion_platform_id(vendorId, vendorPromotionId):
    from encodings import utf_8
    import urllib.parse
    import uuid
    import base64

    unique = uuid.UUID(vendorId)

    encodedVendorId = base64.b64encode(unique.bytes)

    combineToEncode = encodedVendorId.decode("utf_8") + ";" + vendorPromotionId

    encodedCombined = base64.b64encode(combineToEncode.encode("utf-8"))

    promotionPlatformId = urllib.parse.quote_from_bytes(encodedCombined.decode("utf-8"))

    return promotionPlatformId

def create_promotion_platform_id(promotion_v1):
    global vendor_id, date_pattern

    promotion_base = '''
    {
        "_id":"",
        "promotionPlatformId":""
    }
    '''

    promotion_v2 = json.loads(promotion_base)

    if "_id" in promotion_v1:
        promotion_v2["_id"] = promotion_v1["_id"]
    else:
        promotion_v2["_id"] = None

    if "promotionPlatformId" in promotion_v1:
        promotion_v2["promotionPlatformId"] = promotion_v1["promotionPlatformId"]
    else:
        if "vendorId" in promotion_v1 and "vendorPromotionId" in promotion_v1:
            promotion_v2["promotionPlatformId"] = promotion_platform_id(promotion_v1["vendorId"], str(promotion_v1["vendorPromotionId"]))

    return promotion_v2


def run_data_migration(skip, limit):
    global str_date
    promotions_platform_id = []

    promotions_v1_cursor = find_promotions_v1(skip, limit)

    count = 0

    for promotion in promotions_v1_cursor:

        if promotion is None or promotion["_id"] is None: continue
        promotions_platform_id = save_promotion_v2(promotion, promotions_platform_id)
        count += 1

    promotions_v1_cursor.close

    if promotions_platform_id is not None and len(promotions_platform_id) > 0:
        run_bulk_update(promotions_platform_id)

    print("Total de promotions migrados:" + str(count))


def verifyVendorIdAndVendorPromotionIdError():
    global strMongoIn, coll_name_old
    result = False
    client = MongoClient(strMongoIn)

    db = client.Promotion
    collection = db[coll_name_old]
    queryErrorVendorId = {"$or": [{"vendorId": {"$exists": False}}, {"vendorPromotionId": {"$exists": False}}]}
    resultVendorIdOrVendorPromotionId = collection.count_documents(queryErrorVendorId)
    if (resultVendorIdOrVendorPromotionId > 0):
        print("Migration not perfomed, elements without vendorId or vendorPromotionId {}".format(resultVendorIdOrVendorPromotionId))
        result = True
    return result

def count():
    global strMongoIn, coll_name_old, query

    client = MongoClient(strMongoIn)

    db = client.Promotion
    collection = db[coll_name_old]

    result = collection.count_documents(query)

    return result


def main():
    global strMongoIn, strMongoOut, vendor_id, bulk_size, coll_name_old, coll_name_new, limit, thread_number, execution_number

    strMongoIn = os.getenv('STR_MONGO_IN', "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false")
    strMongoOut = os.getenv('STR_MONGO_OUT', "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false")
    bulk_size = int(os.getenv('BULK_SIZE', 10000))
    coll_name_new = os.getenv('COLL_NAME_NEW', "AR-Promotions")
    coll_name_old = os.getenv('COLL_NAME_OLD', "AR-Promotions")
    limit = int(os.getenv('LIMIT', 1100))
    thread_number = int(os.getenv('THREAD_NUMBER', 1))
    execution_number = int(os.getenv('EXECUTION_NUMBER', 1))
    vendor_id = os.getenv('VENDOR_ID', "")

    print("Mongo url: " + strMongoOut)
    print("Vendor Id: " + vendor_id)
    print("Bulk size:" + str(bulk_size))
    print("Number of threads:" + str(thread_number))
    print("Execution number:" + str(execution_number))
    print("Limit:" + str(limit))
    print("New collection name:" + coll_name_new)
    print("Old collection name:" + coll_name_old)


    if verifyVendorIdAndVendorPromotionIdError() == True:
        import sys
        print("Error of data, vendorId or vendorPromotionId don't exists!")
        sys.exit()

    total_migrate = count()
    print("Total to migrate: " + str(total_migrate))

    i = 0
    j = 0

    while i < execution_number:
        print("Execution number:" + str(i))
        threads = list()
        while j < thread_number:
            thread_run = threading.Thread(target=run_data_migration, args=(j * limit, limit))
            print("Thread number iniciou:" + str(j))
            threads.append(thread_run)
            thread_run.start()
            j += 1
        for index, thread in enumerate(threads):
            thread.join()
            print("Thread number terminou:" + str(index))
        i += 1
        thread_number += thread_number

    total_seconds = (datetime.datetime.now(pytz.utc) - init_time).total_seconds()
    print(str(total_seconds) + "s para execucao total")
    client = MongoClient(strMongoOut, serverSelectionTimeoutMS=120000)
    db = client.Promotion
    collection = db[coll_name_new]
    collection.create_index([('promotionPlatformId', pymongo.ASCENDING)], unique=True)


if __name__ == "__main__":
    main()