package com.uniguairaca.chatbot.controller;

import com.uniguairaca.chatbot.service.ChatbotService;

public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }
}
