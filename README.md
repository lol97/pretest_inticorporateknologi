# Pretest Business Logic

This project was build for assignment of pretest at inti corpora teknologi for position backend-engineer.

## Author
- [Sufyan Saori](https://github.com/lol97)

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Gradle](https://gradle.org/)

Optional IDE
- [Eclipse 2020.03](https://www.eclipse.org/downloads/packages/release/2020-03)

## Doc
- the POSTMAN API at `doc/Pretest Corpora Inti Teknologi.postman_collection.json`
- using swagger-doc at `{{base_endpoint}}/swagger-ui.html`

## Build
The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build the project execute the following command:

```bash
  ./gradlew build
```

## Running the application locally
- Config the database at `application-dev.properties`, currently use h2 memory databases. with `dev` as an active profile.

- There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.inticorporateknologi.pretest.PretestApplication` class from your IDE.

- Or After build with gradle you can run with command:

```bash
  java -jar .\build\libs\pretest-0.0.1-SNAPSHOT.jar
```

## Docker Build and Run
- Build the project with following command:

```bash
  ./gradlew build
```

- Create Image with tag name `inticorporateknologi/pretest`:

```bash
  docker build --build-arg JAR_FILE=build/libs/pretest-0.0.1-SNAPSHOT.jar -t inticorporateknologi/pretest .
```

- Run Image with name `pretest_inticorptek` :

```bash
	docker run --name pretest_inticorptek -p 8080:8080 inticorporateknologi/pretest
```
