
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

 - Added basic Jpa Repository
 - Added Hibernate configuration
 - Added Oracle datasource configuration
 - Implemented basic Spring Boot application running on port 8080
 - Added unit test infrastructure with JUnit and AssertJ
 - Added Gradle wrapper --gradle-version 3.2.1
 
## TODO

 - Add Swagger support
 - Implement test, development, production environments
 - Enable logging
 - Add Auditing