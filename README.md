# Skeletal Kotlin Project

## Work Goal 

Model the policy lifecycle.  As a simplification this provides a REST API accepting
events on the policies and generating output events to the log.

The goal was to create the Policies as stateful objects that might be spun up as individual actors. I tinkered with the
policies as actors in coroutines but wasn't happy with it. It was neither cleaner nor faster.  With more resources than
an my creaky 2012 MacBook it might be worth a visit.

### Other Thinking

I wanted this to be event in, event out, allowing for horizontal scaling of this service. The Policies are light weight
and could be persisted easily in a cache or database. 

I put effort into code quality, idiomatic Kotlin, and packaging. I wanted to show my work in the entire life cycle of
development.  

## Employs

 - Gradle 5.1.1
    - Kotlin DSL
    - Kotlin lint
    - Jacoco
    - detekt
 - Kotlin 1.3.20
 - ktor

## Build

```bash
$ ./gradlew clean check
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
$ curl http://localhost:8080/ping
20190131163736 INFO Server: ACK
```

It is if it `ACK`nowledged.

## A Bulk Test

There's a bash script, `scripts/bulk.sh`, that fires up the server and using curl, runs a 1000 policies through their 
life cycle.



 