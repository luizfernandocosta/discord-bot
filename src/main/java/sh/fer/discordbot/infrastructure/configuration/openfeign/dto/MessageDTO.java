package sh.fer.discordbot.infrastructure.configuration.openfeign.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private String role;
    private String content;

}
