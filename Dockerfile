FROM gradle:jdk8-alpine AS GRADLE_BUILD

WORKDIR /workbench
COPY ./ ./

USER root
RUN gradle clean && gradle build

FROM openjdk:8-jre-alpine3.9

COPY --from=GRADLE_BUILD /workbench/build/libs/giftexchange.jar /giftexchange.jar
CMD ["java", "-jar", "/giftexchange.jar"]