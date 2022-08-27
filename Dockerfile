FROM openjdk:17
EXPOSE 8080
ADD target/products_app.jar products_app.jar
ENTRYPOINT ["java", "-jar", "products_app.jar"]