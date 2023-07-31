package sh.fer.discordbot.infrastructure.configuration.openfeign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import sh.fer.discordbot.infrastructure.configuration.openfeign.dto.ChatRequestDTO;
import sh.fer.discordbot.infrastructure.configuration.openfeign.dto.ChatResponseDTO;

@FeignClient(value = "openaiclient", url = "${openai.api-url}")
public interface OpenAiClient {

    @PostMapping(value = "/v1/chat/completions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ChatResponseDTO postResponse(@RequestHeader("Authorization") String token, @RequestBody ChatRequestDTO request);

}
