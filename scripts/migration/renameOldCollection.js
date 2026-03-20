print("Renaming old promotion collection");

db = db.getSiblingDB('Promotion');

db.getCollection(country + '-Promotions').renameCollection(country + "-Promotions_OLD");

print("Promotion collection renamed");