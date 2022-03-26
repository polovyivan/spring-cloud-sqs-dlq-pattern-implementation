#!/bin/bash
echo "########### Starting to create source sqs without attached dlq... ###########"

echo "########### Setting SQS names as env variables ###########"
export DLQ_FOR_SOURCE_SQS_WITHOUT_ATTACHED_DLQ=dlq-for-source-sqs-without-attached-dlq
export SOURCE_SQS_WITHOUT_ATTACHED_DLQ=source-sqs-without-attached-dlq

echo "########### Creating DLQ ###########"
aws --endpoint-url=http://localstack:4566 sqs create-queue --queue-name $DLQ_FOR_SOURCE_SQS_WITHOUT_ATTACHED_DLQ

echo "########### Creating Source Queue ###########"
aws --profile=localstack --endpoint-url=http://localstack:4566 sqs create-queue --queue-name $SOURCE_SQS_WITHOUT_ATTACHED_DLQ

echo "########### Listing queues ###########"
aws --endpoint-url=http://localhost:4566 sqs list-queues
