FROM openjdk:8

EXPOSE 8080

COPY target/export-metrics-to-cloudwatch-1.0.0.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
CMD []