# Description

This is my implementation of a [task](https://github.com/mfindrik/hiring-exercise-java-developer/blob/master/README.md) received from cyan Security Group. This app is an API which accepts a POST HTTP
request with URLs in the payload. The app navigates to those URLs and fetches RSS feeds in XML format. Afterwards, feed
entries are parsed and saved to H2 in-memory database.

# Technologies used

- Spring Boot 2.7
- Rome framework for RSS feeds
- Hibernate 5.6
- H2 database
- ModelMapper
- Commons Validator for URLs
- JUnit 5
- Maven

# How to run it

```shell
mvn spring-boot:run
```
Do a POST request to the endpoint `http://localhost:8080/analyse/new` with a JSON array containing URLs of RSS feeds to consume.

Navigate to `http://localhost:8080/h2-console` to check content of the database.
