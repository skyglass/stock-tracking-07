### 📖 Concurrency and Resiliency Patterns in Saga Transactions for Spring Boot Microservices

#### ✅ Outbox Pattern, Saga, DDD, CQRS, Clean Architecture, Kafka Streaming, Debezium CDC, PostgreSQL, Optimistic Locking
#### ✅ Safe Idempotent Retry Transactions with Optimistic Locking and Spring Retry
#### ✅ Domain-Driven Design, Event Sourcing, CQRS, Clean Code, Hexagonal Architecture
#### ✅ E2E Concurrency Testing Framework with Completable Futures using Spring Cloud OpenFeign

<ul style="list-style-type:disc">
    <li>📖 This <b>Payment Processing Platform with Clean Architecture, Kafka Streaming and Debezium CDC Connect</b> provides fully functional Development Environment:</li>
    <li>📖 <b>Event-Driven Spring Boot Microservices</b> with Clean Architecture, Kafka Streaming, Debezium CDC PostgreSQL Kafka Connect and Outbox Pattern</li>
    <li>📖 <b>Swagger UI Gateway</b> with Keycloak Authorization</li>
    <li>📖 <b>E2E Testing Service</b> with Spring Cloud OpenFeign REST Client</li>
    <li>📖 Local <b>Docker</b> Development Environment</li>
  <li>📖 Full <b>Technology Stack</b>:</li>
  <ul>
    <li>✅ <b>Swagger UI</b></li>
    <li>✅ <b>Spring Cloud OpenFeign</b></li>
    <li>✅ <b>Spring Boot</b></li>
    <li>✅ <b>Spring Cloud Gateway</b></li>
    <li>✅ <b>Event-Driven Microservices</b></li>
    <li>✅ <b>Kafka Event Streaming</b></li>
    <li>✅ <b>Kafka Event Store</b></li>
    <li>✅ <b>Kafka Avro Schema Registry</b></li>
    <li>✅ <b>DDD, Clean Code, Hexagonal Architecture</b></li>
    <li>✅ <b>Event Sourcing, CQRS, Saga with Outbox Pattern, Kafka, Debezium and PostgreSQL</b></li>
    <li>✅ <b>Debezium Change Data Capture PostgreSQL Kafka Connect</b></li>
    <li>✅ <b>Reliable Outbox Pattern Saga Transactions with Kafka, Debezium and PostgreSQL</b></li>
    <li>✅ <b>Safe Idempotent Retry Transactions with Optimistic Locking and Spring Retry</b></li>
    <li>✅ <b>Kafka UI</b></li>
    <li>✅ <b>Query Projection with PostgreSQL Database</b></li>
    <li>✅ <b>Keycloak Oauth2 Authorization Server</b></li>
    <li>✅ <b>Local Docker Environment</b></li>
    <li>✅ <b>E2E Testing Framework</b></li>
    <li>✅ <b>E2E Concurrency Testing with Completable Futures</b></li>
    <li>✅ <b>Remote Debugging</b></li>
    <li>✅ <b>Zipkin Distributed Tracing</b></li>
  </ul>
</ul>

### 📖 Links

- [Stock Tracking Platform with Outbox Pattern, Kafka Event Streaming, Debezium CDC Connector and PostgreSQL](https://github.com/skyglass/stock-tracking-03)
- [Udemy Course: Microservices: Clean Architecture, DDD, SAGA, Outbox & Kafka](https://www.udemy.com/course/microservices-clean-architecture-ddd-saga-outbox-kafka-kubernetes/)
- [Video Streaming Platform with Debezium CDC Kafka Connector, Kafka Event Streaming, Minio File Storage and FFmpeg Video Processing](https://github.com/greeta-video-01/video-api)
- [Stock Tracking Platform with Axon Event Sourcing, CQRS and Saga Transactions](https://github.com/greeta-stock-02/stock-api)
- [E2E Testing Pipeline for Spring Boot Microservices using OpenFeign Client and Github Actions](https://www.linkedin.com/pulse/e2e-testing-pipeline-spring-boot-microservices-using-openfeign/)

### 📖 Step By Step Guide

#### Local Docker Environment Setup:

```
sh docker-start.sh
```

- this script will build docker images and start environment with your code changes

```
sh docker-app-restart.sh order
```

- this script will rebuild spring boot docker image for `order` aplication and restart application with rebuilt image
- replace `order` with the name of the application you want to rebuild and restart


#### Local Docker Environment E2E Testing:

- make sure you started local docker environment with `sh docker-start.sh` command

- run `sh debezium-start.sh` (Debezium Kafka PostgreSQL connectors)

- open http://localhost:9000 in your Browser and make sure that Swagger UI is working

- go to `e2e-service` and run all E2E Tests with this command: `mvn test -Dtest="*E2eTest"`

- alternatively, run each test separately in your IDE

- alternatively, you can run all E2E Tests as part of your Github Actions Pipeline against your cloud environment REST API

- for more details see: [E2E Testing Pipeline for Spring Boot Microservices using OpenFeign Client and Github Actions](https://www.linkedin.com/pulse/e2e-testing-pipeline-spring-boot-microservices-using-openfeign/)

- Warning! In case of any problems with Docker Environment, see `Docker Troubleshooting` section

- Use `Kafka UI` to monitor Topics, Kafka Connectors, Producers, Consumers and Event Messages: http://localhost:8024/

- Use `PostgreSQL` Database Client (for example DBeaver) to monitor `customer` `order` and `payment` databases

- Warning! Before each E2E test, PostgreSQL databases are completely reset: all data is clean before running each E2E Test

- For E2E REST API requests OpenFeign uses  `admin/admin` password credentials (it receives `access_token` from Keycloak Server using `password` grant type)

- Make sure that Keycloak Server is running as part of your Local Docker Environment: http://localhost:8080 (otherwise OpenFeign REST API authorized requests will not work)

- Warning! Sometimes switching between Swagger UI pages doesn't refresh Swagger UI completely and you might see wrong REST endpoints: just refresh the page and continue

- For Swagger UI `POST` requests: click `Authorize` and use `admin/admin` or `user/user` for credentials (`clientId` should be `stock-app`)


### Docker Troubleshooting

- In case of any problems, start crashed spring boot containers again:

```
docker-compose -f docker-app-compose.yml up -d
```

- In case the tests still fail, try to restart all spring boot containers:

```
docker-compose -f docker-app-compose.yml down
docker-compose -f docker-app-compose.yml up -d
```

- In case you still have problems, try to restart all docker containers:

```
sh docker-restart.sh
```


### Remote Debugging

- use `BPL_DEBUG_PORT` property in `docker-app-compose.yml` for remote debugging port of the spring boot application (see `Port` in the screenshot)

- select spring boot application from the dropdown list (see `Use module classpath` in the screenshot)

![Configuration to debug a containerized Java application from IntelliJ IDEA](documentation/06-14.png)

#### Kafka UI

- Kafka UI should be available here: http://localhost:8070/
- You can browse topics, partitions, messages and other kafka resources using this console

#### Zipkin Server

- Zipkin Server for Distributed Tracing is available here: http://localhost:9411/
