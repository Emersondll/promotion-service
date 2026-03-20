#!/bin/bash

users_qty=(10 20 40 80)

for i in "${users_qty[@]}"; do
    sed -i.bak "s/jmeter.users=.*/jmeter.users=$i/g" jmeter.properties
    
    echo "Running with $i users..."
    jmeter -n -t api-invoice.jmx -p jmeter.properties
    echo "Removing test documents"
    python ../helper/remove_all_documents_load_test.py
done

rm *.log
