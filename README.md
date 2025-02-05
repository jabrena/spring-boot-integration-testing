# Spring Boot Integration Testing

This repository show the advantages to add integrations tests in favor of Unit tests with Mocks.

## Design

![](./docs/design.png)

## How to build in local

```bash
sdk env install
./mvnw clean verify
./mvnw dependency:tree
./mvnw clean spring-boot:run
./mvnw clean spring-boot:run -Dspring-boot.run.profiles=ko
curl "http://localhost:8080/gods/greek"

./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
```

## References

- https://martinfowler.com/bliki/IntegrationTest.html
- https://plantuml.com/es/
- https://real-world-plantuml.com/