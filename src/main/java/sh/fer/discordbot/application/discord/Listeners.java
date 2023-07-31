package sh.fer.discordbot.application.discord;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listeners extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        event
             .getGuild()
             .getSystemChannel()
             .sendMessage(String.format("Parece que alguém entrou no discord da 1kilo, não é mesmo? Seja bem-vindo %s, seu frango ✔", event.getMember()))
             .queue();
    }

}
