package sh.fer.discordbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import sh.fer.discordbot.infrastructure.configuration.discord.DiscordBot;

@SpringBootApplication
@EnableFeignClients
public class DiscordBotApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(DiscordBotApplication.class, args);

		DiscordBot discordBot = context.getBean(DiscordBot.class);

		discordBot.startBot();

	}

}
