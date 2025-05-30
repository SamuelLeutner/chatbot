package com.uniguairaca.chatbot.dto;

import lombok.Data;

@Data
public class WhatsappMessageDTO {
  private String sessionId;
  private String from;
  private String type;
  private String text;
  private String messageId;
  private Long timestamp;
}