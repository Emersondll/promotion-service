import pymongo

myclient = pymongo.MongoClient("mongodb://b2b-gb-mongodb-dev:rgb6SqXkvZ3VFgf7Vsjqv68EcH3QZmxRZzvqtZadfZLdSjFnmHEcT3oTRqBmLZidnkzAxw89VVmQHaeJeqVifQ==@b2b-gb-mongodb-dev.documents.azure.com:10255/?ssl=true&replicaSet=globaldb")
mydb = myclient["BR-Promotion"]
mycollection = mydb[""]

myquery = {"_id": {'$regex' : 'TEST-ORIGINAL-.*'}}

mycollection.remove(myquery)
