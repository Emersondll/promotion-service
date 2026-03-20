echo "Creating collections and indexes"

for i in /scripts/*.js; do mongosh "mongodb://localhost:27017" $i; done

echo "Collections and indexes created"