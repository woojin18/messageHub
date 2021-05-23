# production stage
FROM nginx:stable-alpine as production-stage

RUN apk add openjdk8

ARG PROFILES
ENV PROFILES $PROFILES

### FE

EXPOSE 80
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY ./dist /usr/share/nginx/html

### BE

ENV jar_file cm-web-front.jar
ENV be_dir /cm-web-front-be

RUN mkdir ${be_dir}
COPY ${jar_file} ${be_dir}

ENV JAVA_OPTS "-Dspring.profiles.active=$PROFILES"

### run

ENTRYPOINT nginx && java ${JAVA_OPTS} -jar ${be_dir}/${jar_file}
