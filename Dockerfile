FROM artifactory.itg.ti.com/docker-public/library/openjdk:8-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.war
ADD ${JAR_FILE} app.war
ENTRYPOINT [ "java","-jar","/app.war" ]