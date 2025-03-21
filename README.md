# Be or not to be in a Pyramid of Testing

This repository show the advantages to add integrations tests in favor of Unit tests with Mocks.

## Design

![](./docs-analysis/uml-sequence-diagram.png)

## How to build in local

```bash
# Examples
sdk env install
./mvnw clean verify
./mvnw clean integration-test
./mvnw clean verify surefire-report:report
./mvnw clean verify jacoco:report
jwebserver -p 8000 -d "$(pwd)/target/site/"
./mvnw dependency:tree
./mvnw clean spring-boot:run
./mvnw clean spring-boot:run -Dspring-boot.run.profiles=ok
./mvnw clean spring-boot:run -Dspring-boot.run.profiles=local
curl "http://localhost:8080/gods/greek"

./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates

#Slides
jwebserver -p 8000 -d "$(pwd)/docs/"
open http://localhost:8000/
open http://localhost:8000/?print-pdf
```

## References

- https://www.researchgate.net/publication/335809902_Role_of_Testing_in_Software_Development_Life_Cycle
- https://www.functionize.com/blog/how-nasa-does-software-testing-and-qa
- https://github.com/mockito/mockito/wiki/How-to-write-good-tests
- https://martinfowler.com/bliki/GivenWhenThen.html
- https://martinfowler.com/tags/testing.html
- https://martinfowler.com/tags/test%20categories.html
- https://martinfowler.com/tags/extreme%20programming.html
- https://martinfowler.com/bliki/IntegrationTest.html
- https://microsoft.github.io/code-with-engineering-playbook/automated-testing/unit-testing/mocking/
- https://kentcdodds.com/blog/the-testing-trophy-and-testing-classifications
- https://engineering.atspotify.com/2018/01/testing-of-microservices/
- https://x.com/randal_olson/status/799707563860299776
- https://x.com/erinfranmc/status/1148986961207730176
- https://kentcdodds.com/blog/write-tests
- https://plantuml.com/es/
- https://real-world-plantuml.com/
- https://plantuml.com/en/sequence-diagram
- https://github.com/plantuml-stdlib/C4-PlantUML
- https://c4model.com/
- https://testing.googleblog.com/
- https://revealjs.com/
- https://revealjs.com/installation/#full-setup


Powered by [Cursor AI IDE](https://www.cursor.com/)