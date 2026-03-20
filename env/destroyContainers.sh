#!/bin/bash

docker-compose down -v
echo "Down containers"
docker network rm $(docker network ls | grep "promotion-network" | awk '// { print $1 }')
echo "Deleted promotion-network"
docker rm -f $(docker ps -a -q --filter "name=rabbitmq" | sort -u)
echo "Deleted docker rabbitmq"
docker rmi -f $(docker image ls -q --filter "reference=rabbitmq*" | sort -u)
echo "Deleted images rabbitmq*"
