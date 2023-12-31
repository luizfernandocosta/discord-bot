package sh.fer.discordbot.infrastructure.configuration.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {

    public final AudioPlayer audioPlayer;
    public BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer player) {

        this.audioPlayer = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void getQueue(AudioTrack track) {
        if (!this.audioPlayer.startTrack(track,true)) {
            this.queue.offer(track);
        }
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return this.queue;
    }

    public void nextTrack() {
        this.audioPlayer.startTrack(this.queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {

        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        player.stopTrack();
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        player.startTrack(player.getPlayingTrack(),true);
    }

}
