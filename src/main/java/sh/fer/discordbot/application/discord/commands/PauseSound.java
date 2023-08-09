package sh.fer.discordbot.application.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import sh.fer.discordbot.application.discord.CommandManagerService;
import sh.fer.discordbot.infrastructure.configuration.lavaplayer.PlayerManager;

import java.util.List;

public class PauseSound implements CommandManagerService {
    @Override
    public String getName() {
        return "pausar";
    }

    @Override
    public String getDescription() {
        return "Pausa a música atual";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        if(PlayerManager.get().getMusicManager(event.getGuild()).trackScheduler.audioPlayer.isPaused()) {
            PlayerManager.get().getMusicManager(event.getGuild()).trackScheduler.audioPlayer.setPaused(false);
        } else {
            PlayerManager.get().getMusicManager(event.getGuild()).trackScheduler.audioPlayer.setPaused(true);
        }

    }
}
