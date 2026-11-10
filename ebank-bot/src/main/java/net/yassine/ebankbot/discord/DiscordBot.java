package net.yassine.ebankbot.discord; // use your actual package

import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.yassine.ebankbot.agents.EbankAIAgent;

@DiscordController
public class DiscordBot {
    private final EbankAIAgent ebankAIAgent;

    public DiscordBot(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String query = event.getMessage().getContentRaw();
        if (query.isBlank()) return;

        // Same user in the same channel = same conversation (memory works)
        String conversationId = event.getChannel().getId() + ":" + event.getAuthor().getId();

        String response = ebankAIAgent.chat(query, conversationId);

        // Discord refuses messages longer than 2000 characters
        if (response.length() > 2000) {
            response = response.substring(0, 1997) + "...";
        }
        event.getChannel().sendMessage(response).queue();
    }
}