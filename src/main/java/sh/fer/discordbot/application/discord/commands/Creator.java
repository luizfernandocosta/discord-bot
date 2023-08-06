package sh.fer.discordbot.application.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import sh.fer.discordbot.application.discord.CommandManagerService;
import sh.fer.discordbot.infrastructure.configuration.logging.Log;

import java.awt.*;
import java.util.List;

public class Creator implements CommandManagerService {

    @Override
    public String getName() {
        return "criador";
    }

    @Override
    public String getDescription() {
        return "Retorna o criador do bot";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        try {

            Member member = event.getGuild().getMemberById(135857221547327489L);

            User user = event.getUser();

//            user.openPrivateChannel().flatMap(privateChannel -> privateChannel.sendMessage("E aí, cuzão")).queue();
//
//            event.reply(String.format("Criado pelo único %s <a:lustra:763092953576374283> <t:1618953630>" , member.getUser().getName())).queue();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("1kilo-bot", null, "https://i.imgur.com/4E3pm4U.gif");
            embedBuilder.setThumbnail("https://i.imgur.com/4E3pm4U.gif");
            embedBuilder.setTitle("<:sap:835729425365598258> ### AGORA TOCANDO <:sap:835729425365598258>");
            embedBuilder.addField("ARTISTA", "audioInfo.author", false);
            embedBuilder.addField("MÚSICA", "audioInfo.title", false);
            embedBuilder.addField("DURAÇÃO", "String.format(%s:%s, minutes, seconds)", false);
            embedBuilder.addField("LINK", "audioInfo.uri", false);
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
