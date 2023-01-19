# Basf Coding Challenge

Coding challenge for BASF

## Environment Set Up

Create image of the project:

```
docker image build -t patents:v1 .
```

NOTE: specify the version of the image created in the docker compose file

In order to get our environments ready to test, we need to create the MongoDB database.

To start it, execute this command in a terminal within the root of the application:

```
docker-compose up -d
```

## APIs available

All information about the available endpoints can be found under:

```
http://localhost:8082/swagger-ui
```