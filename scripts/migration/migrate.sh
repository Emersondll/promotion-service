#!/bin/bash

read -r -p "Enter the the number of type of migration to migrate (1-promotionV2/2-promotionPlatformId: " typeMigration
read -r -p "Enter the country to migrate: " country

read -r -p "Paste the database connection string: " databaseUri

if [ "$typeMigration" = "1" ]; then
  read -r -p "Paste the default vendor id to be used: " vendorId
fi

read -r -p "Do you want to install the dependencies of the script? (y/n): " willInstall

if [ "$willInstall" = "y" ]; then
  pip3 install pymongo
  pip3 install dnspython
  pip3 install pytz
  pip3 install python-bsonjs
  pip3 install requests
  pip3 install bsonjs
fi

if [ "$typeMigration" = "1" ]; then
  read -r -p "Do you want to rename the old collection to prefix _OLD? (y/n): " willRename
fi

if [ "$willRename" = "y" ]; then
  export COLL_NAME_OLD=$country-Promotions_OLD
  export COLL_NAME_NEW=$country-Promotions
  mongo "$databaseUri" --eval "var country = '$country'" --quiet renameOldCollection.js
else
  export COLL_NAME_OLD=$country-Promotions
  export COLL_NAME_NEW=$country-Promotions_MULTI_VENDOR_TEMP
fi

if [ "$typeMigration" = "1" ]; then
  read -r -p "Do you want fixing endDates with high value? (y/n): " willFixDates
fi

if [ "$willFixDates" = "y" ]; then
  export COLL_NAME_OLD=$country-Promotions_OLD
  export COLL_NAME_NEW=$country-Promotions
  mongo "$databaseUri" --eval "var country = '$country'" --quiet fixInvalidEndDate.js
fi

if [ "$typeMigration" = "2" ]; then
  export COLL_NAME_NEW=$country-Promotions
fi

read -r -p "Do you want to run before check? (y/n): " willBeforeCheck

if [ "$willBeforeCheck" = "y" ]; then
  mongo "$databaseUri" --eval "var collectionName = '$COLL_NAME_OLD'" --quiet count.js
fi

read -r -p "Enter the number of threads: " threads

read -r -p "Enter the limit of elements by thread: " limit

export STR_MONGO_IN=$databaseUri
export STR_MONGO_OUT=$databaseUri
export BULK_SIZE=10000
export SKIP=0
export LIMIT=$limit
export THREAD_NUMBER=$threads
export EXECUTION_NUMBER=1
export VENDOR_ID=$vendorId

if [ "$typeMigration" = "2" ]; then
  python3 platformID.py
else
  python3 multivendor.py
fi

echo "Migration concluded"