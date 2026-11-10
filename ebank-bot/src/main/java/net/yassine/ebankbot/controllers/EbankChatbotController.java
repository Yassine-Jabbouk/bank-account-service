package net.yassine.ebankbot.controllers;

import net.yassine.ebankbot.agents.EbankAIAgent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class EbankChatbotController {
    private final EbankAIAgent ebankAIAgent;

    public EbankChatbotController(ChatClient.Builder chatClient, ChatMemory chatMemory, EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query,
            @RequestParam(name = "conversationId", defaultValue = "default") String conversationId) {
        return ebankAIAgent.chat(query, conversationId);
    }
    @GetMapping("/chatStream")
    public Flux<String> chatStream(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query,
            @RequestParam(name = "conversationId", defaultValue = "default") String conversationId) {
        return ebankAIAgent.chatStream(query, conversationId);
    }
}