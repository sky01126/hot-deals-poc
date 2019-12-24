# Docker 빌드 : mvn dockerfile:build
# Docker RUN : docker run -e JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Dserver.port=8080 -Dfile.encoding=UTF8 -Dclient.encoding.override=UTF-8 -Duser.timezone=GMT+09:00 -Dsun.java2d.opengl=false -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dspring.profiles.active=dev -Dserver.logs.home=/home/server/logs" -p 8080:8080 -i -t -d kthcorp/kthcorp-base-webapp:1.0.0.RELEASE

FROM sky01126/openjdk:11.0.2

RUN set -eux && mkdir -p /home/server

WORKDIR /home/server
VOLUME /home/server/logs

ARG JAR_PATH
ARG JAR_FILE

ADD ${JAR_PATH}/${JAR_FILE} /home/server/app.jar
RUN bash -c 'touch /home/server/app.jar'
ENTRYPOINT exec java $JAVA_OPTS -jar /home/server/app.jar
