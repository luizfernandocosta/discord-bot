FROM 192.168.15.130:32002/eclipse-temurin:17.0.6_10-jre-focal

RUN mkdir /home/spring

COPY target/discord-bot-0.0.1-SNAPSHOT.jar /home/spring/

WORKDIR /home/spring

CMD ["java", "-jar", "discord-bot-0.0.1-SNAPSHOT.jar"]