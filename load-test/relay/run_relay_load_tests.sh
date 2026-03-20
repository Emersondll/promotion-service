#!/bin/bash

users_qty=(10 20 40 80)

for i in "${users_qty[@]}"; do
    sed -i.bak "s/jmeter.users=.*/jmeter.users=$i/g" jmeter.properties
    
    echo "Running with $i users..."
    jmeter -n -t relay-promotion.jmx -p jmeter.properties
    echo "Removing test documents"

done

rm *.log