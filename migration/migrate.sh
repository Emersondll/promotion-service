#!/usr/bin/env bash

image=beesmicroservices.azurecr.io/promotion-migration:latest

set -eu

echo "Starting promotion migration"

read -r -p "Enter the country to migrate: " country
read -r -p "Paste the database connection string: " databaseUri
read -r -p $'Choose the migration operation.\x0a 1 - Migration.\x0a 2 - Check migration result. \x0a: ' operation

if [ "$operation" = "2" ]; then
  mongo "$databaseUri" --eval "var country = '$country'" --quiet afterCheck.js
  exit
fi

platformIdEnabled=true

read -r -p "Do you want to check the amount of registers to migrate (y/n): " beforeCheck
if [ "$beforeCheck" = "y" ]; then
  mongo "$databaseUri" --eval "var country = '$country'" --quiet beforeCheck.js
fi

read -r -p "The amount of register to migrate: " total
read -r -p "Enter the page size: " pageSize
read -r -p "Enter the node amount desired: " nodePool
read -r -p "Enter the thread pool size: " threads

docker build -t $image .

for ((i = 1; i <= nodePool; i++)); do
  docker run -d --rm --name promotion-migration-"$i" \
    --env MIGRATION_SIZE="$total" \
    --env MIGRATION_COUNTRY="$country" \
    --env MIGRATION_NODE="$i" \
    --env MIGRATION_NODE_POOL="$nodePool" \
    --env MIGRATION_DATABASE_CONNECTION="$databaseUri" \
    --env MIGRATION_PAGE_SIZE="$pageSize" \
    --env MIGRATION_THREADS_AMOUNT="$threads" \
    --env MIGRATION_ENABLED="$platformIdEnabled" \
    --env LOGGING_FILE_NAME=/migration/logs/app.log \
    --volume "$(pwd)"/logs/"$country"/"$i":/migration/logs \
    $image
done
