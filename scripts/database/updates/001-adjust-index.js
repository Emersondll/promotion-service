db = db.getSiblingDB( "Promotion" );

db.getCollectionNames().forEach(
    collection=>{

        if (collection.match(/^..-Promotions$/)) {
            print("Updating indexes for " + collection.toString());
            db.getCollection(collection).createIndex({vendorId: 1, endDate: 1, deleted: 1, startDate: 1, promotionType: 1 } );
            db.getCollection(collection).createIndex({promotionPlatformId: 1, endDate: 1, deleted: 1, startDate: 1, promotionType: 1 } );
            db.getCollection(collection).dropIndex({endDate:1,deleted:1,startDate:1,promotionType:1,vendorId:1});
            db.getCollection(collection).dropIndex({promotionPlatformId:1});
        }
    }
);

