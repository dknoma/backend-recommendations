FROM openjdk:11-jre

ADD target/jersey-backened-template-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/maven-docker/jersey-backened-template-1.0-SNAPSHOT-jar-with-dependencies.jar

ENTRYPOINT ["java", "-jar", "/opt/maven-docker/jersey-backened-template-1.0-SNAPSHOT-jar-with-dependencies.jar"]