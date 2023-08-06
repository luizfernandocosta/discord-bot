package sh.fer.discordbot.infrastructure.configuration.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {

    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    private PlayerManager() {

        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager );

    }

    public static PlayerManager get() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager musicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
            return musicManager;
        });
    }

    public void loadAndPlay(SlashCommandInteractionEvent event, String trackURL) {

        final GuildMusicManager musicManager = this.getMusicManager(event.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {

                musicManager.trackScheduler.queue(audioTrack);

                try {
                    if (musicManager.trackScheduler.getQueue().size() == 0) {

                        final AudioTrackInfo audioInfo = audioTrack.getInfo();
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

                    } else {

                        event.reply("Adicionada a fila...").queue();

                    }
                } catch (Exception e) {
                    event.reply("Houve um erro ao buscar informações do artista").queue();
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                final List<AudioTrack> tracks = audioPlaylist.getTracks();

                if (!tracks.isEmpty()) {
                    musicManager.trackScheduler.queue(tracks.get(0));
//                    textChannel.sendMessage("Tocando música " + tracks.get(0).getInfo().title).queue();
                }

            }

            @Override
            public void noMatches() {
//                textChannel.sendMessage("Não foi encontrada nenhuma música com esse nome!").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });
    }


}
