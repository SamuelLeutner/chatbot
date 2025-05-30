package com.uniguairaca.chatbot.dto.response;

import lombok.Data;

@Data
public class QRCodeResponseDTO {
  private String status;
  private String message;
  private String sessionId;
  private String qrCodeBase64;

  public QRCodeResponseDTO() {
  }

  public QRCodeResponseDTO(String qrCodeBase64, String sessionId, String status, String message) {
    this.qrCodeBase64 = qrCodeBase64;
    this.sessionId = sessionId;
    this.status = status;
    this.message = message;
  }
}