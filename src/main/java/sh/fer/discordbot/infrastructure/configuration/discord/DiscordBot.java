package sh.fer.discordbot.infrastructure.configuration.discord;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sh.fer.discordbot.application.discord.CommandManager;
import sh.fer.discordbot.application.discord.Listeners;
import sh.fer.discordbot.application.discord.commands.*;
import sh.fer.discordbot.infrastructure.configuration.openfeign.OpenAiClient;

@Component
@Slf4j
public class DiscordBot {

    @Value("${discord.token}")
    private String discordToken;

    private JDA jda;

    @Autowired
    private OpenAiClient openAiClient;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api-key}")
    private String apiKey;

    public void startBot() {

        try {
            this.jda = JDABuilder
                                 .createDefault(discordToken)
                                 .enableIntents(GatewayIntent.GUILD_MEMBERS)
                                 .setStatus(OnlineStatus.DO_NOT_DISTURB)
                                 .setActivity(Activity.playing("BOT DA 1KILO TM"))
                                 .build();

            this.jda.addEventListener(new Listeners());

            CommandManager commandManager = new CommandManager();
            commandManager.add(new Creator());
            commandManager.add(new PlaySound());
            commandManager.add(new NowPlaying());
            commandManager.add(new SkipSound());
            commandManager.add(new StopSound());
            commandManager.add(new QueueSound());
            commandManager.add(new PauseSound());
            commandManager.add(new SearchChatGPT(openAiClient, apiKey, model));
            this.jda.addEventListener(commandManager);

        } catch (Exception e) {
            log.error("An error has occurred: {}", (Object) e.getStackTrace());
        }

    }

}
