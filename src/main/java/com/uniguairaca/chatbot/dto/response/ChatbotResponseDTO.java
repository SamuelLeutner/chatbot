package com.uniguairaca.chatbot.dto.response;

import lombok.Data;

@Data
public class ChatbotResponseDTO {
  private String to;
  private String message;
  private String sessionId;
  private String originalMessageId;
  
  public ChatbotResponseDTO() {
  }
  
  public ChatbotResponseDTO(String to, String message, String sessionId, String originalMessageId) {
    this.to = to;
    this.message = message;
    this.sessionId = sessionId;
    this.originalMessageId = originalMessageId;
  }
}