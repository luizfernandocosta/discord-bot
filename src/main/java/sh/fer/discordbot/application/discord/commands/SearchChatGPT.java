package sh.fer.discordbot.application.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import sh.fer.discordbot.application.discord.CommandManagerService;
import sh.fer.discordbot.application.discord.dto.OpenAiRequestDTO;
import sh.fer.discordbot.application.discord.dto.OpenAiResponseDTO;
import sh.fer.discordbot.infrastructure.configuration.logging.Log;
import sh.fer.discordbot.infrastructure.configuration.openfeign.OpenAiClient;
import sh.fer.discordbot.infrastructure.configuration.openfeign.dto.ChatRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class SearchChatGPT implements CommandManagerService {

    private final OpenAiClient openAiClient;

    private final String token;

    private final String model;

    @Autowired
    public SearchChatGPT(OpenAiClient openAiClient, String token, String model) {
        this.openAiClient = openAiClient;
        this.token = token;
        this.model = model;
    }

    @Override
    public String getName() {
        return "procurar";
    }

    @Override
    public String getDescription() {
        return "Faz um prompt ao ChatGPT";
    }

    @Override
    public List<OptionData> getOptions() {

        List<OptionData> options = new ArrayList<>();

        options.add(new OptionData(OptionType.STRING, "prompt", "prompt para mandar ao ChatGPT", true));

        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        try {

            event.deferReply(false).queue();

            ModelMapper modelMapper = new ModelMapper();

            OpenAiRequestDTO request = new OpenAiRequestDTO(
                    this.model,
                    200, event.
                    getOption("prompt").getAsString()
            );

            OpenAiResponseDTO response = modelMapper.map(this.openAiClient.postResponse("Bearer " + this.token, modelMapper.map(request, ChatRequestDTO.class)), OpenAiResponseDTO.class);

            event.getHook().sendMessage(response.getChoices().get(0).getMessage().getContent()).queue();

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
