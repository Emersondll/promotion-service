print("Counting amount of promotions to migrate");

db = db.getSiblingDB('Promotion');

let collection = db.getCollection(collectionName);

const nowFormatted = setDate();

print("Total in collection: " + collection.count());
print("Total to migrate: " + collection.count( { endDate: { $gte: ISODate(nowFormatted) }, deleted: {  $eq: false } } ));

function setDate() {
	let now = new Date();
	now.setDate(now.getDate());
	return now.toISOString();
}