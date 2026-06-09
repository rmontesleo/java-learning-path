package com.demo.busy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;

public class SimpleReasoningChat {
    
    public static void main(String[] args) {
        
        // Validate API key early and add diagnostics when calling the model.
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("OPENAI_API_KEY is not set. Set the environment variable and retry.");
            return;
        }

        // TODO: In temperature, docs say higher is more creative. Verify what happened with temperature; keep 1.0 for now
        // How is using a reasoning model, the configuration change from a completion chat
        ChatModel chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(OpenAiChatModelName.GPT_5_NANO)
                .reasoningEffort("low")
                .temperature(1.0)
                .timeout(Duration.ofSeconds(120))
                .maxCompletionTokens(4096)
                .logRequests(true)
                .logResponses(true)
                .build();

        List<ChatMessage> messages = new ArrayList<>();
        SystemMessage systemMessage = new SystemMessage("Answer briefly. Do not reason deeply. Use less than 100 words.");
        UserMessage userMessage = new UserMessage("How should I learn Java?");
        messages.add(systemMessage);
        messages.add(userMessage);

        ChatResponse answer = chatModel.chat(messages);

        System.out.println("finishReason: " + answer.metadata().finishReason());
        System.out.println("metadata: " + answer.metadata());

        String text = answer.aiMessage().text();

        if(  text == null || text.isBlank() ){
            System.out.println("No visible text returned. Likely all completion tokens were spent on reasoning.");
        }else {
            System.out.println(text);
        }




        
    }
}
