#!/bin/bash

docker network rm $(docker network ls | grep "promotion-network" | awk '// { print $1 }')
echo "Recive network docker"
docker rm -f $(docker ps -a -q --filter "name=rabbitmq" | sort -u)
echo "Delete rabbitmq"
docker network create "promotion-network"
echo "Created promotion-network"
echo "Loading compose..."
docker-compose -f ./docker-compose.yml up -d --build
