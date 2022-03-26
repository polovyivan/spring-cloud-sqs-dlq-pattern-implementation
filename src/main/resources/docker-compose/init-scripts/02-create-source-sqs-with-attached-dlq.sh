#!/bin/bash
echo "########### Starting to create source sqs without attached dlq... ###########"

echo "########### Setting SQS names as env variables ###########"
export DLQ_FOR_SOURCE_SQS_WITH_ATTACHED_DLQ=dlq-for-source-sqs-with-attached-dlq
export SOURCE_SQS_WITH_ATTACHED_DLQ=source-sqs-with-attached-dlq

echo "########### Creating DLQ ###########"
aws --endpoint-url=http://localstack:4566 sqs create-queue --queue-name $DLQ_FOR_SOURCE_SQS_WITH_ATTACHED_DLQ

echo "########### ARN for DLQ ###########"
DLQ_FOR_SOURCE_SQS_WITH_ATTACHED_DLQ_ARN=$(aws --endpoint-url=http://localstack:4566 sqs get-queue-attributes\
                  --attribute-name QueueArn --queue-url=http://localhost:4566/000000000000/"$DLQ_FOR_SOURCE_SQS_WITH_ATTACHED_DLQ"\
                  |  sed 's/"QueueArn"/\n"QueueArn"/g' | grep '"QueueArn"' | awk -F '"QueueArn":' '{print $2}' | tr -d '"' | xargs)


echo "########### Creating Source queue ###########"
aws --profile=localstack --endpoint-url=http://localstack:4566 sqs create-queue --queue-name $SOURCE_SQS_WITH_ATTACHED_DLQ \
     --attributes '{
                   "RedrivePolicy": "{\"deadLetterTargetArn\":\"'"$DLQ_FOR_SOURCE_SQS_WITH_ATTACHED_DLQ_ARN"'\",\"maxReceiveCount\":\"2\"}",
                   "VisibilityTimeout": "10"
                   }'


echo "########### Listing Source SQS Attributes ###########"
aws --endpoint-url=http://localstack:4566 sqs get-queue-attributes\
                  --attribute-name All --queue-url=http://localhost:4566/000000000000/"$SOURCE_SQS_WITH_ATTACHED_DLQ"