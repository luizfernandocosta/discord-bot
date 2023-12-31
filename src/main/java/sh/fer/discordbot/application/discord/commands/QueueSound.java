package sh.fer.discordbot.application.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import sh.fer.discordbot.application.discord.CommandManagerService;

import java.util.Collections;
import java.util.List;

public class QueueSound implements CommandManagerService {
    @Override
    public String getName() {
        return "lista";
    }

    @Override
    public String getDescription() {
        return "Mostra lista de músicas";
    }

    @Override
    public List<OptionData> getOptions() { return Collections.emptyList(); }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

//        try {
//
//            Member member = event.getMember();
//            Member self = event.getGuild().getSelfMember();
//
//            GuildVoiceState memberVoiceState = member.getVoiceState();
//            GuildVoiceState selfVoiceState = self.getVoiceState();
//
//            CheckIfUserIsInChannel channel = new CheckIfUserIsInChannel(event, memberVoiceState, selfVoiceState);
//            channel.checkUserAndReply();
//
//            GuildMusicManager guildMusicManager = PlayerManager.get().getMusicManager(event.getGuild());
//            List<AudioTrack> queue = new ArrayList<>(guildMusicManager.getTrackScheduler().getQueue());
//
//            guildMusicManager.getTrackScheduler().nextTrack();
//
//            EmbedBuilder embedBuilder = new EmbedBuilder();
//            embedBuilder.setTitle("Lista atual");
//
//            if(queue.isEmpty()) {
//                embedBuilder.setDescription("Lista está vazia");
//            }
//            for(int i = 0; i < queue.size(); i++) {
//                AudioTrackInfo info = queue.get(i).getInfo();
//                embedBuilder.addField(i+1 + ":", info.title, false);
//            }
//
//            event.replyEmbeds(embedBuilder.build()).queue();
//
//            Log.logInfo(
//                    event.getMember().getUser().getName(),
//                    event.getMember().getUser().getId(),
//                    this.getName(),
//                    event.getGuild().getName(),
//                    event.getGuild().getId()
//            );
//
//        } catch (Exception e) {
//
//            Log.logError(
//                    this.getName(),
//                    event.getMember().getUser().getName(),
//                    event.getMember().getUser().getId()
//            );
//
//        }
    }
}
