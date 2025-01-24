#!/bin/bash
awslocal dynamodb create-table \
    --table-name TodoTable\
    --key-schema AttributeName=PK,KeyType=HASH \
    --attribute-definitions AttributeName=PK,AttributeType=S \
    --billing-mode PAY_PER_REQUEST \
    --region ap-northeast-1;
