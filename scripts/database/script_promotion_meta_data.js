db = db.getSiblingDB("Promotion");

db.getCollectionNames().forEach(
    collection=>{
        let promotionCollection = collection.match(/-Promotions/)
                    ? db.getCollection(collection).updateMany({"deleted": {$exists:false}},{$set:{"deleted": false}})
                    : "not found"; print(promotionCollection);
        }
);
