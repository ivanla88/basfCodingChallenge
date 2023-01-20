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

## Mongo Express

Mongo Express is available for managing visually the database and collections:

```
http://localhost:8081/
```

## Brief Explanation about the design

* Code organization by feature

* Created two endpoints:
  * POST /api/v1/patents that uploads a file. Needs to have a body with form-data file named ```file```
  * DELETE /api/v1/patents that erases the content of the database

* Collection ```chemicalPatents``` created to store the information processed 

* Scheduled task executed every 5 minutes that reads and input directory to find new files to process

* The file uploaded through the controller and the ones processed within the scheduled job are moved to a loaded files directory

* OpenNLP used as NER library to analyze the content of the abstract text of the patents

* The entities identified by the NER library are stored in the same document as the information of the patent processed

* Data used to tokenized and tag the text are in English.
  (Possible improvement: detect language and have files to process different languages)

* Dictionary created by me with certain number of chemical words to filter the entities obtain by the NER library





