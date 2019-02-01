#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}" )" > /dev/null 2>&1 && pwd -P)"

PORT=8090
URL=http://localhost:${PORT}
COUNT=1000

cd ${SCRIPT_DIR}/..

java -jar build/libs/*.jar --port ${PORT} &

sleep 2

curl ${URL}/ping

echo Create ${COUNT} policies.
for (( COUNTER=1; COUNTER<=${COUNT}; COUNTER+=1 )); do
     curl -X POST ${URL}/policies/${COUNTER}\?duration\=2
done

echo Premiums incoming.
for (( COUNTER=1; COUNTER<=${COUNT}; COUNTER+=1 )); do
     curl -X PUT ${URL}/policies/${COUNTER}/PREMIUM_RECEIVED
done

echo Active one month.
for (( COUNTER=1; COUNTER<=${COUNT}; COUNTER+=1 )); do
     curl -X PUT ${URL}/policies/${COUNTER}/ACTIVE_ONE_MONTH
done

echo Another month.
for (( COUNTER=1; COUNTER<=${COUNT}; COUNTER+=1 )); do
     curl -X PUT ${URL}/policies/${COUNTER}/ONE_MONTH
done

echo Term complete.
for (( COUNTER=1; COUNTER<=${COUNT}; COUNTER+=1 )); do
     curl -X PUT ${URL}/policies/${COUNTER}/TERM_COMPLETE
done

curl -X GET ${URL}/policies

kill -9 $(ps | grep dltt | grep -v grep | cut -d ' ' -f 1)