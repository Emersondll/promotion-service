FROM adoptopenjdk:17-jre-hotspot

LABEL maintainer="silvio.junior@ab-inbev.com"
LABEL description Image for start promotion migration

ARG JAR_FILE=target/promotion-migration-*.jar

COPY ${JAR_FILE} /migration/app.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /migration/app.jar ${0} ${@}"]