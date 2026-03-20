print("\n\n Before check \n")


db = db.getSiblingDB('Promotion');

let promotionCollection = db.getCollection(country + '-Promotions');

const nowFormatted = setDate();

print("# Promotion");
print("Total with platformId: " + promotionCollection.count({ promotionPlatformId: { $exists: true } }));
print("Total: " + promotionCollection.count() + "\n");

function setDate() {
	let now = new Date();
	now.setDate(now.getDate() - 2);

	return now.toISOString();
}
