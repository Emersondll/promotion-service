print("Fixing dates with high value");
db = db.getSiblingDB('Promotion');
db.getCollection(country+"-Promotions_OLD").updateMany(
    { endDate: { $gt: ISODate('2060-04-03T00:00:00.000Z')}, deleted: false },
    {
        "$set": {
            "endDate": ISODate('2060-04-03T00:00:00.000Z')
        }
    }, {
        multi: true
    }
);
print("Dates fixed");