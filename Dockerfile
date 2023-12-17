FROM eclipse-temurin:20
WORKDIR /app
#RUN addgroup --system javauser && adduser -S -s /usr/sbin/nologin -G javauser javauser
COPY target/stock-service-0.0.1-SNAPSHOT.jar /app/stock-service.jar
#RUN chown -R javauser:javauser .
#USER javauser
ENTRYPOINT ["java", "-jar", "stock-service.jar"]
