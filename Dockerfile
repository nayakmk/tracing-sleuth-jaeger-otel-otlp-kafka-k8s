FROM openjdk:18-jdk-alpine
MAINTAINER nayakmk.io
COPY build/libs/tracer-sleuth-k8s-0.0.1-SNAPSHOT.jar tracer-sleuth-k8s-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/tracer-sleuth-k8s-0.0.1-SNAPSHOT.jar"]