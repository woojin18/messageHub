# production stage
FROM ectech.co.kr:35001/nginx-jdk8:1.0.0 as production-stage

RUN echo ${PROFILES}

### FE

EXPOSE 80
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY ./dist /usr/share/nginx/html

### BE

ENV jar_file cm-web-front.jar
ENV be_dir /cm-web-front-be

RUN mkdir ${be_dir}
COPY ${jar_file} ${be_dir}

### run

ENTRYPOINT nginx && java -Dspring.profiles.active=${PROFILES} -jar ${be_dir}/${jar_file}
