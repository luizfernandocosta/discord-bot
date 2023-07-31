package sh.fer.discordbot.infrastructure.configuration.discord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIfUserIsInChannel {

    private SlashCommandInteractionEvent event;
    private GuildVoiceState memberVoiceState;
    private GuildVoiceState selfVoiceState;

    public void checkUserAndJoinChannel() {

        if(!this.getMemberVoiceState().inAudioChannel()) {
            this.getEvent().reply("Você precisa estar em um canal de voz").queue();
            return;
        }

        if (!this.getSelfVoiceState().inAudioChannel()) {
            this.getEvent().getGuild().getAudioManager().openAudioConnection(this.getMemberVoiceState().getChannel());
        } else {
            if (this.getSelfVoiceState().getChannel() != this.getMemberVoiceState().getChannel()) {
                this.getEvent().reply("Você precisa estar no mesmo canal de voz que eu!").queue();
                return;
            }
        }
    }

    public void checkUserAndReply() {

        if(!this.getMemberVoiceState().inAudioChannel()) {
            this.getEvent().reply("Você precisa estar em um canal!").queue();
            return;
        }

        if(!this.getSelfVoiceState().inAudioChannel()) {
            this.getEvent().reply("Não estou em nenhum canal!").queue();
            return;
        }

        if(this.getSelfVoiceState().getChannel() != this.getMemberVoiceState().getChannel()) {
            this.getEvent().reply("Você não está no mesmo canal que eu!").queue();
            return;
        }
    }
}
