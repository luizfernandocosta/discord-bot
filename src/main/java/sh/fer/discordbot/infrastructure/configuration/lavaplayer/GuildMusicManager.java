package sh.fer.discordbot.infrastructure.configuration.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.LinkedList;
import java.util.Queue;

public class GuildMusicManager {

    public final AudioPlayer audioPlayer;

    public final TrackScheduler trackScheduler;
    private AudioPlayerSendHandler sendHandler;



    public GuildMusicManager(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.trackScheduler = new TrackScheduler(this.audioPlayer);
        this.audioPlayer.addListener(this.trackScheduler);
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return this.sendHandler;
    }


}

