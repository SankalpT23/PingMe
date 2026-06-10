package com.project.PingMe.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIEnhancerService {
    //Spring Injects The AI Model
    @Autowired(required = true)
    private ChatModel chatModel;

    public String enhanceMessage(String message) {
        String promt = "You are a professional corporate communicator. " +
                "Rewrite the following raw message into a highly professional, " +
                "polite, and concise corporate notification alert. " +
                "Do not add any extra explanations, just output the final message. " +
                "Enhance this notification message to be more polite and professional. You must keep the final response strictly under 200 characters. Do not include any conversational filler."+
                "Raw Message: " + message;

        //Send OpenAI the prompt and Take the Message
        String enchancedMessage = chatModel.call(promt);
        //Return the refined message back to pipeline
        return enchancedMessage;
    }
}
