
## Production

## Development




## Testing


## Gradle tasks

### Clean build folder
```
./gradlew clean
```

### Run tests
```
./gradlew test --info
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


### Run a jar file from console
```
$java -cp build/libs/humanResources.jar org.humanResources.service.Main Reader
```

## ChangeLog

 - Added unit test infrastructure with JUnit and AssertJ
 - Added gradle wrapper --gradle-version 3.2.1
 
## TODO

 - Prepare Gradle build environment
 - Implement test, development, production environments