package com.uniguairaca.chatbot.dto.request;

import lombok.Data;

@Data
public class QRCodeRequestDTO {
  private String sessionId;
  private String callbackUrl;

  public QRCodeRequestDTO() {
  }

  public QRCodeRequestDTO(String sessionId, String callbackUrl) {
    this.sessionId = sessionId;
    this.callbackUrl = callbackUrl;
  }
}