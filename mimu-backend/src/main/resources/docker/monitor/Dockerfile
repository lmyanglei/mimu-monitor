FROM java:8
VOLUME /tmp
ADD mimu-backend-1.0.2.jar monitor.jar
EXPOSE 9877
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/monitor.jar"]
