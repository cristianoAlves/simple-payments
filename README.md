# simple-payments

## Running the application using docker compose
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