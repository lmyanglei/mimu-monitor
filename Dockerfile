FROM java:8

MAINTAINER mimu

RUN mkdir -p /mimu

WORKDIR /mimu

EXPOSE 8088

ADD ./mimu-backend/target/mimu-backend.jar ./

CMD sleep 30;java -Djava.security.egd=file:/dev/./urandom -jar mimu-backend.jar