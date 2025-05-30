package com.uniguairaca.chatbot.dto;

import com.uniguairaca.chatbot.enums.MessageType;

import lombok.Data;

@Data
public class RabbitMessageDTO<T> {
  private T payload; 
  private MessageType type; 
  private String correlationId;

  public RabbitMessageDTO() {
  }

  public RabbitMessageDTO(MessageType type, T payload, String correlationId) {
    this.type = type;
    this.payload = payload;
    this.correlationId = correlationId;
  }
}