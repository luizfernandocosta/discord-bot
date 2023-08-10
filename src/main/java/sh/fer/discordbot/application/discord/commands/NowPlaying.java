package sh.fer.discordbot.application.discord.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
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
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Collections;
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
        return Collections.emptyList();
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

            GuildMusicManager guildMusicManager = PlayerManager.get().getMusicManager(event.getGuild());
            AudioPlayer audioPlayer = guildMusicManager.audioPlayer;

            if (audioPlayer.getPlayingTrack() == null) {
                event.reply("Não há nenhuma música tocando no momento!").queue();
                return;
            }

            final AudioTrackInfo audioInfo = audioPlayer.getPlayingTrack().getInfo();
            long durationInMillis = audioInfo.length;
            TemporalAccessor temporalAccessor = LocalDateTime.now();

            long minutes = (durationInMillis / 1000) / 60;
            long seconds = (durationInMillis / 1000) % 60;

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("1kilo-bot", null, "https://cdn.discordapp.com/attachments/706938155437391883/1136685176219508891/Sem_Titulo-59.jpg");
            embedBuilder.setThumbnail("https://cdn3.emoji.gg/emojis/7670-musicbeat.gif");
            embedBuilder.setTitle("<:sap:835729425365598258> AGORA TOCANDO <:sap:835729425365598258>");
            embedBuilder.addField("<:music_microphone:1136859876090462228> ARTISTA <:music_microphone:1136859876090462228>", audioInfo.author, false);
            embedBuilder.addField("<a:music_note:1136850717693456445>  MÚSICA <a:music_note:1136850717693456445>", audioInfo.title, false);
            embedBuilder.addField("<a:music_time:1136856943361802330> DURAÇÃO <a:music_time:1136856943361802330>", String.format("%s:%s", minutes, seconds), false);
            embedBuilder.addField("LINK", audioInfo.uri, false);
            embedBuilder.setColor(Color.PINK);
            embedBuilder.setTimestamp(temporalAccessor);
            embedBuilder.setFooter("ARE YOU READY?", "https://cdn.discordapp.com/attachments/706938155437391883/1136685176219508891/Sem_Titulo-59.jpg");

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
                    event.getMember().getUser().getId(),
                    e
            );

        }
    }
}
