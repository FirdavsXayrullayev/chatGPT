package ChatGPT.chat.controller;

import ChatGPT.chat.dto.ChatGptRequest;
import ChatGPT.chat.dto.ChatGptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.client.AiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
@RequiredArgsConstructor
public class ChatController {
    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String url;
    @Autowired
    private RestTemplate restTemplate;

    private final AiClient aiClient;

    @GetMapping("/chat")
    public String chatGptResponse(@RequestParam("prompt") String prompt){
        ChatGptRequest request = new ChatGptRequest(model, prompt);
        ChatGptResponse chatGptResponse = restTemplate.postForObject(url, request, ChatGptResponse.class);
        return chatGptResponse != null ? chatGptResponse.getChoices().get(0).getMessage().getContent() : null;
    }
    @GetMapping("/song")
    public String topSong(@RequestParam String prompt){
        return aiClient.generate(prompt);
    }
}
