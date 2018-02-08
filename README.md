# Brigade SQS Gateway
Takes events from an SQS queue and turns them into Kubernetes Secrets that [Brigade](https://github.com/Azure/brigade) can understand and process.

# Running
Brigade SQS Gateway is a Spring Boot project. You can run the project by calling `mvn spring-boot:run`.

## Configuration
You can configure the application via environment variables as documented below.

- `AWS_SQS_QUEUE` - the AWS Queue Name to listen to (e.g. `brigade-test`)
- `K8S_NAMESPACE` - the namespace to create secrets in
- `K8S_MASTER` - the Kubernetes API address
- `K8S_TOKEN` - the service account token to use to authenticate
- `CLOUD_AWS_REGION_STATIC` - the AWS region you are running in (defaults to `us-east-1`)

# Event Format
This project expects a payload in the format of the [SqsBrigadeEvent](src/main/java/com/lewisheadden/brigadesqsgateway/sqs/SqsBrigadeEvent.java) class.

Currently this looks like

```json
{
  "project": "my-project",
  "type": "my-trigger",
  "payload": "{\"Hello\": \"World!\"}"
  "script": "console.log(\"Hello!\")"
}
```