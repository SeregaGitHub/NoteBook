FROM amazoncorretto:11-alpine-jdk

COPY . /testfolder

WORKDIR /testfolder

RUN javac /testfolder/src/Main.java

CMD ["java", "src/Main.java"]

ENV TZ=Europe/Moscow