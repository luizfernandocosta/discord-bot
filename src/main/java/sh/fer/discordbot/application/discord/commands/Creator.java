package sh.fer.discordbot.application.discord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import sh.fer.discordbot.application.discord.CommandManagerService;
import sh.fer.discordbot.infrastructure.configuration.logging.Log;

import java.util.Collections;
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
        return Collections.emptyList();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        try {

            Member member = event.getGuild().getMemberById(135857221547327489L);

            event.reply(String.format("Criado pelo único %s <a:lustra:763092953576374283> <t:1618953630>" , member.getUser().getName())).queue();

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
