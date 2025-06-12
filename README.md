# simple-payments
https://github.com/PicPay/picpay-desafio-backend?tab=readme-ov-file

## Technologies
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
* [Maven](https://maven.apache.org/)
* [Mapstruct](https://mapstruct.org/)
* [Docker compose](https://docs.docker.com/compose/)
* [Springdoc](https://springdoc.org/)
* [Spring Actuator](https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/) as part of Observability
* [Liquibase](https://www.liquibase.com/) as database versioning
* [PostgreSQL](https://www.postgresql.org/) as a database

## Architecture
Based on [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
```
src/main/java
└── com/simple/payments
    |── adapters/ 
    │   ├── inbound
    │   └── outbound
    ├── application 
    ├── config/
    ├── domain/
    └── PaymentsApplication.java
```
## Design

## Running the application using docker compose
### Clone this repository:
```
git clone git@github.com:cristianoAlves/simple-payments.git
```

#### From app root directory:
```
docker compose up -d --build
```
### Test API
`curl http://localhost:8081/accounts`
### Test using actuator
`curl http://localhost:8081/actuator`

### API documentation
http://localhost:8081/swagger-ui/index.html
