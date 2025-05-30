package com.uniguairaca.chatbot.service;

import org.springframework.stereotype.Service;

import com.uniguairaca.chatbot.amqp.sender.RabbitMQSender;
import com.uniguairaca.chatbot.dto.RabbitMessageDTO;
import com.uniguairaca.chatbot.dto.request.QRCodeRequestDTO;
import com.uniguairaca.chatbot.dto.response.ChatbotResponseDTO;
import com.uniguairaca.chatbot.dto.response.QRCodeResponseDTO;
import com.uniguairaca.chatbot.enums.MessageType;


import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture; 

@Service
public class WhatsappBrokerGateway {
  private final RabbitMQSender rabbitMQSender;
  private final ConcurrentHashMap<String, CompletableFuture<QRCodeResponseDTO>> qrCodeFutures = new ConcurrentHashMap<>();

  public WhatsappBrokerGateway(RabbitMQSender rabbitMQSender) {
    this.rabbitMQSender = rabbitMQSender;
  }

  public CompletableFuture<QRCodeResponseDTO> requestQRCode(QRCodeRequestDTO request) {
    String correlationId = UUID.randomUUID().toString();
    CompletableFuture<QRCodeResponseDTO> future = new CompletableFuture<>();
    qrCodeFutures.put(correlationId, future); 

    RabbitMessageDTO<QRCodeRequestDTO> rabbitMessage = new RabbitMessageDTO<>(MessageType.QR_CODE_REQUEST, request, correlationId);
    rabbitMQSender.send(rabbitMessage);

    return future;
  }

  public void sendChatbotResponse(ChatbotResponseDTO response) {
    RabbitMessageDTO<ChatbotResponseDTO> rabbitMessage = new RabbitMessageDTO<>(MessageType.CHATBOT_MESSAGE_SEND, response, null); 
    rabbitMQSender.send(rabbitMessage);
  }

  public void completeQRCodeRequest(QRCodeResponseDTO response, String correlationId) {
    CompletableFuture<QRCodeResponseDTO> future = qrCodeFutures.remove(correlationId);
    if (future != null) {
      future.complete(response); 
    } else {
      System.out.println("Erro: correlationId não encontrado para resposta de QR Code: " + correlationId);
    }
  }
}