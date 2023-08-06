package sh.fer.discordbot.application.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import sh.fer.discordbot.application.discord.CommandManagerService;

import java.util.List;

public class Help implements CommandManagerService {
    @Override
    public String getName() {
        return "ajuda";
    }

    @Override
    public String getDescription() {
        return "Lista os comandos disponíveis para o servidor";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        User user = event.getUser();

        EmbedBuilder embedBuilder = new EmbedBuilder();

        if (user.hasPrivateChannel()) {
            user.openPrivateChannel().flatMap(privateChannel -> privateChannel.sendMessageEmbeds(new EmbedBuilder().build())).queue();
        }

    }
}
