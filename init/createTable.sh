#!/bin/bash
awslocal dynamodb create-table \
    --table-name TodoTable\
    --key-schema AttributeName=id,KeyType=HASH \
    --attribute-definitions AttributeName=id,AttributeType=S \
    --billing-mode PAY_PER_REQUEST \
    --region ap-northeast-1;
