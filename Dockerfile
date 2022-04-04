FROM openjdk:8
ADD target/shop-0.0.1-SNAPSHOT.jar shop-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "shop-0.0.1-SNAPSHOT.jar"]