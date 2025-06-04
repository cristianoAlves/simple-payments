# simple-payments

#### Start the PostgreSQL Docker Container

#### Running postgres standalone

```
docker run --name payments-postgres \
  -e POSTGRES_DB=paymentsdb \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=mysecretpassword \
  -p 5432:5432 \
  -d postgres:15
```