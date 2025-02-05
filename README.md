# Spring Boot Integration Testing

<div hidden>
```
@startuml firstDiagram

Alice -> Bob: Hello
Bob -> Alice: Hi!
		
@enduml
```
</div>

![](firstDiagram.svg)

## How to build in local

```bash
sdk env install
./mvnw clean verify

./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
```

## References

- https://martinfowler.com/bliki/IntegrationTest.html
- https://plantuml.com/es/
- https://real-world-plantuml.com/