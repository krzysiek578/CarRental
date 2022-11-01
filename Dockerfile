# define base docker image
FROM openjdk:17
EXPOSE 8080
ADD target/DatabaseAndSpringBoot-0.0.1-SNAPSHOT.jar databaseandspringboot.jar
ENTRYPOINT ["java", "-jar", "/databaseandspringboot.jar"]