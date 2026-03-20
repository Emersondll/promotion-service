import json

import pymongo
import pytz
import bsonjs
import os
import threading
from pymongo import MongoClient, InsertOne
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

query = {'endDate': {'$gt': datetime.datetime.strptime(str_date, date_pattern)}, 'deleted': False}


def find_promotions_v1(skip, limit):
    global strMongoIn, date_pattern, str_date, coll_name_old, query

    print("Buscando documentos de " + str(skip) + " a " + str(skip + limit))

    client = MongoClient(strMongoIn)

    db = client.Promotion
    collection = db[coll_name_old]

    cursor = collection.find(query).skip(skip).limit(limit)

    client.close

    return cursor


def clean_collection_v2():
    global strMongoOut, coll_name_new, init_time

    print("Removendo promotions da collection:", coll_name_new)

    client = MongoClient(strMongoOut)

    db = client.Promotion
    collection = db[coll_name_new]

    myquery = {}

    collection.delete_many(myquery)

    client.close

    total_seconds = (datetime.datetime.now(pytz.utc) - init_time).total_seconds()

    print(str(total_seconds) + "s para limpar a collection multi-vendor")

    print("multi-vendor promotions deleted")


def run_bulk_insert(promotions):
    global init_time, strMongoOut, coll_name_new

    total_seconds = (datetime.datetime.now(pytz.utc) - init_time).total_seconds()
    print(str(total_seconds) + "s para iniciar o import para base de " + str(len(promotions)) + " registros")

    client = MongoClient(strMongoOut, serverSelectionTimeoutMS=120000)

    db = client.Promotion
    collection = db[coll_name_new]
    requesting = []

    for json_obj in promotions:
        if json_obj is None: continue
        raw_bson = bsonjs.loads(json.dumps(json_obj))
        bson_record = RawBSONDocument(raw_bson)
        requesting.append(InsertOne(bson_record))

    collection.create_index([('description', 'text'), ('title', 'text')])
    collection.create_index([('vendorId', pymongo.ASCENDING), ('vendorPromotionId', pymongo.ASCENDING)], unique=True)
    collection.create_index([('endDate', pymongo.ASCENDING)], expireAfterSeconds=172800)

    try:
        collection.bulk_write(requesting, False)
        total_seconds = (datetime.datetime.now(pytz.utc) - init_time).total_seconds()
        print(str(total_seconds) + "s para o bulk write na collection:" + str(coll_name_new))
    except BulkWriteError as error:
        print(str(error.details['writeErrors'][0]['errmsg']))
        pass

    client.close


def save_promotion_v2(promotion_v1, promotions_v2):
    global bulk_size

    promotions_v2.append(create_promotion_v2(promotion_v1))

    if len(promotions_v2) == bulk_size:
        run_bulk_insert(promotions_v2)
        promotions_v2 = []

    return promotions_v2


def convert_entity(entity):
    if entity == "SKU":
        return "VENDOR_ITEM"
    else:
        return entity


def copy_entityDetail(promotion_v1):
    if "entityDetail" in promotion_v1 and promotion_v1["entityDetail"] is not None and not isinstance(
            promotion_v1["entityDetail"], int) and not isinstance(promotion_v1["entityDetail"], float):
        return True
    else:
        return False


def create_promotion_v2(promotion_v1):
    global vendor_id, date_pattern

    promotion_base = '''
    {
        "_id":"",
        "vendorPromotionId":"",
        "vendorId":"",
        "title":"",
        "description":"",
        "promotionType":"",
        "startDate":{
              "$date":"2021-01-04T00:00:00.000Z"
           },
        "endDate":{
              "$date":"2099-12-31T23:59:59.999Z"
           },
        "image":"",
        "budget":"",
        "deleted":"",
        "quantityLimit":"",
        "disable":"", 
        "updateAt":{
          "$date":"2021-01-10T03:00:00.001Z"
       }
    }
    '''

    promotion_v2 = json.loads(promotion_base)

    if "_id" in promotion_v1:
        promotion_v2["_id"] = promotion_v1["_id"]
        promotion_v2["vendorPromotionId"] = promotion_v1["_id"]
    else:
        promotion_v2["_id"] = None
        promotion_v2["vendorPromotionId"] = None

    promotion_v2["vendorId"] = vendor_id

    if "description" in promotion_v1:
        promotion_v2["description"] = promotion_v1["description"]
    else:
        promotion_v2["description"] = None

    if "title" in promotion_v1:
        promotion_v2["title"] = promotion_v1["title"]
    else:
        promotion_v2["title"] = None

    if "promotionType" in promotion_v1:
        promotion_v2["promotionType"] = promotion_v1["promotionType"]
    else:
        promotion_v2["promotionType"] = None

    if "image" in promotion_v1:
        promotion_v2["image"] = promotion_v1["image"]
    else:
        promotion_v2["image"] = None

    if "budget" in promotion_v1:
        promotion_v2["budget"] = promotion_v1["budget"]
    else:
        promotion_v2["budget"] = None

    if "deleted" in promotion_v1:
        promotion_v2["deleted"] = promotion_v1["deleted"]
    else:
        promotion_v2["deleted"] = None

    if "quantityLimit" in promotion_v1:
        promotion_v2["quantityLimit"] = promotion_v1["quantityLimit"]
    else:
        promotion_v2["quantityLimit"] = None

    if "disable" in promotion_v1:
        promotion_v2["disable"] = promotion_v1["disable"]
    else:
        promotion_v2["disable"] = None

    if "startDate" in promotion_v1 and promotion_v1["startDate"] is not None:
        promotion_v2["startDate"]["$date"] = promotion_v1["startDate"].strftime(date_pattern)[:-3] + 'Z'
    else:
        promotion_v2["startDate"] = None

    if "endDate" in promotion_v1 and promotion_v1["endDate"] is not None:
        promotion_v2["endDate"]["$date"] = promotion_v1["endDate"].strftime(date_pattern)[:-3] + 'Z'
    else:
        promotion_v2["endDate"] = None

    if "updateAt" in promotion_v1 and promotion_v1["updateAt"] is not None:
        promotion_v2["updateAt"]["$date"] = promotion_v1["updateAt"].strftime(date_pattern)[:-3] + 'Z'
    else:
        promotion_v2["updateAt"]["$date"] = "2022-01-01T03:00:00.001Z"

    return promotion_v2


def run_data_migration(skip, limit):
    global str_date
    promotions_v2 = []

    promotions_v1_cursor = find_promotions_v1(skip, limit)

    count = 0

    for promotion in promotions_v1_cursor:

        if promotion is None or promotion["_id"] is None: continue

        promotions_v2 = save_promotion_v2(promotion, promotions_v2)
        count += 1

    promotions_v1_cursor.close

    if promotions_v2 is not None and len(promotions_v2) > 0:
        run_bulk_insert(promotions_v2)

    print("Total de promotions migrados:" + str(count))


def count():
    global strMongoIn, coll_name_old, query

    client = MongoClient(strMongoIn)

    db = client.Promotion
    collection = db[coll_name_old]

    result = collection.count_documents(query)

    return result


def main():
    global strMongoIn, strMongoOut, vendor_id, bulk_size, coll_name_old, coll_name_new, limit, thread_number, execution_number

    strMongoIn = os.getenv('STR_MONGO_IN', "")
    strMongoOut = os.getenv('STR_MONGO_OUT', "")
    bulk_size = int(os.getenv('BULK_SIZE', 10000))
    coll_name_new = os.getenv('COLL_NAME_NEW', "")
    coll_name_old = os.getenv('COLL_NAME_OLD', "")
    limit = int(os.getenv('LIMIT', 2300))
    thread_number = int(os.getenv('THREAD_NUMBER', 10))
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

    # clean_collection_v2()

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


if __name__ == "__main__":
    main()
