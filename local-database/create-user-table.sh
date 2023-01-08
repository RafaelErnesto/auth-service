#!/bin/bash

echo "..... setting up configuration......."
aws configure set aws_access_key_id default_access_key --profile=default
aws configure set aws_secret_access_key default_secret_key --profile=default
aws configure set aws_session_token default_security_token --profile=default
aws configure set region us-east-1 --profile=default

if [ -e $pid ]; then
echo "... CREATING User TABLE ......."
aws dynamodb create-table --endpoint-url $AWS_ENDPOINT_URL \
    --table-name $AWS_DYNAMO_TABLE_NAME \
    --attribute-definitions AttributeName=email,AttributeType=S\
      AttributeName=status,AttributeType=S\
    --key-schema AttributeName=email,KeyType=HASH\
      AttributeName=status,KeyType=RANGE\
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
fi
