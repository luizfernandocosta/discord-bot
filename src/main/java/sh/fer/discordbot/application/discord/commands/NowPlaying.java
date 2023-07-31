package sh.fer.discordbot.application.discord.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import sh.fer.discordbot.application.discord.CommandManagerService;
import sh.fer.discordbot.infrastructure.configuration.discord.CheckIfUserIsInChannel;
import sh.fer.discordbot.infrastructure.configuration.lavaplayer.GuildMusicManager;
import sh.fer.discordbot.infrastructure.configuration.lavaplayer.PlayerManager;
import sh.fer.discordbot.infrastructure.configuration.logging.Log;

import java.awt.*;
import java.util.List;

public class NowPlaying implements CommandManagerService {

    @Override
    public String getName() {
        return "agoratocando";
    }

    @Override
    public String getDescription() {
        return "Mostra a música que está tocando agora";
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

            AudioTrackInfo info = guildMusicManager.getTrackScheduler().getPlayer().getPlayingTrack().getInfo();

            long durationInMillis = info.length;

            long minutes = (durationInMillis / 1000) / 60;
            long seconds = (durationInMillis / 1000) % 60;

            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setTitle(":musical_note:  AGORA TOCANDO :musical_note:");
            embedBuilder.setDescription("**Nome:** " + info.title);
            embedBuilder.addField("Autor", info.author, false);
            embedBuilder.addField("Duração",String.format("%s:%s", minutes, seconds), false);
            embedBuilder.addField("Link da música", info.uri, false);
            embedBuilder.setColor(Color.PINK);

            event.replyEmbeds(embedBuilder.build()).queue();

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
