#!/usr/bin/env bash

i=1
cat "data.csv" |sed $'s/\r$//'| while read line
do
    test $i -eq 1 && ((i=i+1)) && continue
    curl -X POST -H "Content-Type: text/plain" --data "$line" http://localhost:8080/orderbook/update
done
