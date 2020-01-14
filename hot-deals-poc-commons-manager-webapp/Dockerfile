# Docker 빌드 : mvn dockerfile:build
# Docker RUN : docker run -e JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Dserver.port=8080 -Dfile.encoding=UTF8 -Dclient.encoding.override=UTF-8 -Duser.timezone=GMT+09:00 -Dsun.java2d.opengl=false -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dspring.profiles.active=dev -Dserver.logs.home=/home/server/logs" -p 8080:8080 -i -t -d hot-deals-poc-webapp:1.0.0.RELEASE

#FROM openjdk:8-jdk-slim
FROM openjdk:8-jdk

ARG TINI_DOWNLOAD_URL="https://github.com/krallin/tini/releases/download/v0.18.0/tini-static-amd64"

ARG JAR_PATH
ARG JAR_FILE

RUN set -eux                                                \
    && cd /etc && rm -rf ./localtime                        \
    && mkdir -p /logs                                       \
    && ln -s ../usr/share/zoneinfo/Asia/Seoul localtime     \
    # Tini Add
    && curl -fL ${TINI_DOWNLOAD_URL} -o /sbin/docker-init   \
    && chmod +x /sbin/docker-init

ADD ${JAR_PATH}/${JAR_FILE} /was/app.jar

RUN bash -c 'touch /was/app.jar'

VOLUME [ "/was", "/logs" ]

ENTRYPOINT [ "/sbin/docker-init", "--" ]
CMD exec java $JAVA_OPTS -jar /was/app.jar
