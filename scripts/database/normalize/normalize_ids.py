import os
import pymongo
import pytz
import copy
import uuid
import sys
import time
from bson.objectid import ObjectId
from datetime import datetime
import dns.resolver
dns.resolver.default_resolver = dns.resolver.Resolver(configure=False)
dns.resolver.default_resolver.nameservers = ['8.8.8.8']


class Normalize_ids:
    def __init__(self, country, environment, mongo_client, promotion_db):
        self.country = country
        self.environment = environment
        self.mongo_client = mongo_client
        self.promotion_db = promotion_db
        self.run()

    def run(self):
        print("\nStarting conversion for environment: ", " and country: ", sep=self.environment, end=self.country)
        coll = self.promotion_db[self.country + "-Promotions"]
        query = { "_id": {"$not": { "$type": "objectId" }}}
        toFix = coll.find(query)
        documents_qtd = coll.count_documents(query)
        print("\nNumber of documents to update: ", documents_qtd)

        if documents_qtd == 0:
            print("\nNo string id for country ", self.country)
            time.sleep(2)
        else:
            duplicates = list(copy.deepcopy(toFix))
            print("\nConverting to ObjectId in memory")
            for item in duplicates:
                queryForDeleting = {"_id":str(item["_id"])}
                print("\nOld id ", item["_id"])
                item["_id"] = ObjectId()
                print("\nDeleting the 'old' items")
                x = coll.delete_one(queryForDeleting)
                print(x.deleted_count, " document deleted for country ", self.country)
                print("\nNew id ", item["_id"])
                print("\nInserting the new items with fixed ObectId as _id: ", item)
                try:
                    coll.insert_one(item)
                    documents_qtd -= 1
                    print("\nNumber of documents to update: ",documents_qtd)
                except Exception as error:
                    print("\nUnexpected error: ", error)
                    print(item)
                    sys.exit(1)

def main():
    init_time = datetime.now(pytz.utc)
    environment = "dev"
    mongo_client = pymongo.MongoClient("mongodb+srv://promotion:xg9EdLhcurJ3xnU65vjhNtXTZN3rBU@europe-dev.bro0e.azure.mongodb.net/admin?authSource=admin&w=majority&readPreference=secondary&appname=MongoDB%20Compass&retryWrites=true&ssl=true")
    promotion_db = mongo_client["Promotion"]
    countries = ["AQ", "AR", "BR", "CA", "CL", "CO", "DE", "DO", "EC", "ES", "GB", "HN", "ID", "MX", "PA", "PE", "PY", "SV", "TZ", "US", "UY", "ZA"]
    print("Number of countries ", len(countries))
    for country in countries:
        Normalize_ids(
            country,
            environment,
            mongo_client,
            promotion_db
        )

    mongo_client.close()

    print("\nStart time", init_time)
    print("Stop time", datetime.now(pytz.utc))

if __name__ == "__main__":
    main()