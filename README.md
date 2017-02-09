## Technologies

- Java 1.8
- Scala
- Spring Boot
- Gradle
- Hibernate 
- Database Oracle 11g 


## Production

Run standalone from console
```
java -jar build/libs/humanResources-0.0.1-SNAPSHOT.war
```

## Development

Build
```
./gradlew build --info
```

Build skip tests
```
./gradlew clean build -x test --info
```



Run

Run standalone with Gradle
```
./gradlew clean bootRun
```

## Testing

### Run tests
```
./gradlew test --info
```

## Gradle tasks

### Clean build folder
```
./gradlew clean
```



### List Gradle tasks

```
./gradlew -q tasks
```

### Generate jar file
```
./gradlew build --info
```

### Compile Java files
```
./gradlew compileJava
```

### List dependencies

```
./gradlew -q dependencies --configuration compile
./gradlew -q dependencies --configuration testCompile
```

### Run a jar file from console
```
$java -cp build/libs/humanResources.jar org.humanResources.service.Main Reader
```

### Install Oracle dependencies
```
mvn install:install-file -Dfile=ojdbc7.jar  -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1 -Dpackaging=jar
```

## ChangeLog

 - Integrated JWT (Json Web Token) for stateless webservice authentication
 - Enabled Spring Security
 - Added custom JSON object mapper
 - Added support for Scala
 - Added Integration Tests to account entity
 - Integrated QueryDSL for building type-safe SQL queries
 - Refactored into Gradle subprojects
 - Configured logging for integration tests
 - Implementing integration test infrastructure: empty Oracle tables and reset sequences before each test
 - Added basic Jpa Repository
 - Added Hibernate configuration
 - Added Oracle datasource configuration
 - Implemented basic Spring Boot application running on port 8080
 - Added unit test infrastructure with JUnit and AssertJ
 - Added Gradle wrapper --gradle-version 3.2.1
 
## TODO

 - Add Swagger support
 - Implement test, development, production environments
 - Add Auditing
 
 
 ## Credits
 
 http://www.svlada.com/jwt-token-authentication-with-spring-boot/