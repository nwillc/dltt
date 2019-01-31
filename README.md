# Skeletal Kotlin Project

## Work Goal 

Model the policy lifecycle.  As a simplification this provides a REST API accepting
events on the policies and generating output events to the log.

## Build

```bash
$ ./gradlew jar
```

## Running Server

```bash
# Build first then...
$ java -jar ./build/libs/*.jar

# To change the port...
$ java -jar ./build/libs/*.jar --port 8090

```

## Hitting The Server

Is it up?

```bash
$ curl http://localhost:8080/api/ping
20190131163736 INFO Server: ACK
```

It is if it `ACK`nowledged.

## Employs

 - Gradle 5.1.1
    - Kotlin DSL
    - Kotlin lint
 - Kotlin 1.3.20
 - ktor
 