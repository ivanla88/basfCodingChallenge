FROM adoptopenjdk/openjdk11
COPY ./target/chemicals-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'mkdir /usr/app/sources'
RUN sh -c 'mkdir /usr/app/sources/loaded'
ENTRYPOINT ["java","-jar","chemicals-0.0.1-SNAPSHOT.jar"]