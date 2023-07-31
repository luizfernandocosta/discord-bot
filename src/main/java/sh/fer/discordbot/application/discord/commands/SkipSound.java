package sh.fer.discordbot.application.discord.commands;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import sh.fer.discordbot.application.discord.CommandManagerService;
import sh.fer.discordbot.infrastructure.configuration.discord.CheckIfUserIsInChannel;
import sh.fer.discordbot.infrastructure.configuration.lavaplayer.GuildMusicManager;
import sh.fer.discordbot.infrastructure.configuration.lavaplayer.PlayerManager;
import sh.fer.discordbot.infrastructure.configuration.logging.Log;

import java.util.List;

public class SkipSound implements CommandManagerService {

    @Override
    public String getName() {
        return "pular";
    }

    @Override
    public String getDescription() {
        return "Pular música atual";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        try {

            Member member = event.getMember();
            Member self = event.getGuild().getSelfMember();

            GuildVoiceState memberVoiceState = member.getVoiceState();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            CheckIfUserIsInChannel channel = new CheckIfUserIsInChannel(event, memberVoiceState, selfVoiceState);
            channel.checkUserAndReply();

            GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
            guildMusicManager.getTrackScheduler().nextTrack();

            event.reply("Pulando música...").queue();

            Log.logInfo(
                    event.getMember().getUser().getName(),
                    event.getMember().getUser().getId(),
                    this.getName(),
                    event.getGuild().getName(),
                    event.getGuild().getId()
            );

        } catch (Exception e) {

            Log.logError(
                    this.getName(),
                    event.getMember().getUser().getName(),
                    event.getMember().getUser().getId()
            );

        }

    }
}
