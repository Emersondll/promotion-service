for i in *.js; do mongosh "mongodb://localhost:27017" $i; done
