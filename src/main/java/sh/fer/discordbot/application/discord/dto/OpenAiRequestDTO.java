package sh.fer.discordbot.application.discord.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiRequestDTO {

    private String model;
    private List<MessageDTO> messages;
//    private int n;
//    private double temperature;
    private int max_tokens;

    public OpenAiRequestDTO(String model, int max_tokens, String prompt) {
        this.model = model;
        this.max_tokens = max_tokens;

        this.messages = new ArrayList<>();
        this.messages.add(new MessageDTO("user", prompt));
    }

}
