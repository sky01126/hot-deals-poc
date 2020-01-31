FROM openjdk:8-jdk

ARG TINI_DOWNLOAD_URL="https://github.com/krallin/tini/releases/download/v0.18.0/tini-static-amd64"

ARG JAR_PATH=target
ARG JAR_FILE=ROOT.jar

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
