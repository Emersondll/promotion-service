import os
import pymongo
import pytz
import copy
import sys
import time
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
        query = { "promotionPlatformId": {"$regex": "%3D"}}
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
                queryForDeleting = {"promotionPlatformId":str(item["promotionPlatformId"])}
                print("\nOld id ", item["promotionPlatformId"])
                new_platform_id = item["promotionPlatformId"].replace("%3D","=")
                x = coll.delete_one(queryForDeleting)
                item["promotionPlatformId"] = new_platform_id
                print("\nDeleting the 'old' items")
                print(x.deleted_count, " document deleted for country ", self.country)
                print("\nNew id ", item["promotionPlatformId"])
                print("\nInserting the new items with fixed promotionPlatformId: ", item)
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
    environment = "sit"
    mongo_client = pymongo.MongoClient("")
    promotion_db = mongo_client["Promotion"]
    countries = ["US"]
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