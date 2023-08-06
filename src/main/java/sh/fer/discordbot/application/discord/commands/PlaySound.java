package sh.fer.discordbot.application.discord.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import sh.fer.discordbot.application.discord.CommandManagerService;
import sh.fer.discordbot.infrastructure.configuration.discord.CheckIfUserIsInChannel;
import sh.fer.discordbot.infrastructure.configuration.lavaplayer.PlayerManager;
import sh.fer.discordbot.infrastructure.configuration.logging.Log;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PlaySound implements CommandManagerService {

    @Override
    public String getName() {
        return "tocar";
    }

    @Override
    public String getDescription() {
        return "Tocar uma música";
    }

    @Override
    public List<OptionData> getOptions() {

        List<OptionData> options = new ArrayList<>();

        options.add(new OptionData(OptionType.STRING, "nome", "Nome da música que você quer tocar", true));

        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        try {

            Member member = event.getMember();
            Member self = event.getGuild().getSelfMember();

            GuildVoiceState memberVoiceState = member.getVoiceState();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            CheckIfUserIsInChannel channel = new CheckIfUserIsInChannel(event, memberVoiceState, selfVoiceState);
            channel.checkUserAndJoinChannel();

            String link = event.getOption("nome").getAsString();

            if (!isUrl(link)) {
                link = "ytsearch:" + link + " audio";
            }

            PlayerManager.get().loadAndPlay(event, link);

            Log.logInfo(
                    event.getMember().getUser().getName(),
                    event.getMember().getUser().getId(),
                    this.getName(),
                    event.getGuild().getName(),
                    event.getGuild().getId()
            );

        } catch (Exception e) {

            e.printStackTrace();

            Log.logError(
                    this.getName(),
                    event.getMember().getUser().getName(),
                    event.getMember().getUser().getId()
            );

        }
    }

    public boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
