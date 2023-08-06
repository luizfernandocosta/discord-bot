package sh.fer.discordbot.application.discord;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildDeafenEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.session.SessionDisconnectEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class Listeners extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

//        event.getGuild().addRoleToMember(event.getMember(), event.getJDA().getRoleById(1135633096167931985L));

        event
             .getGuild()
             .getSystemChannel()
             .sendMessage(String.format("Parece que alguém entrou no discord da 1kilo, não é mesmo? Seja bem-vindo %s, seu frango ✔", event.getMember()))
             .queue();

    }
}
