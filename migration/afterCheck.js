db = db.getSiblingDB('Promotion');
let promotionCollection = db.getCollection(country + '-Promotions');

print("\n\n After check \n");

print("# Promotion");
print("Total migrated: " + promotionCollection.find({promotionPlatformId: {$exists: true}}).count());
print("Total after migration: " + promotionCollection.count());
print("\n");
