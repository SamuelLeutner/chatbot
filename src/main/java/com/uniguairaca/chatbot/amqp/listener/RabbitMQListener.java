package com.uniguairaca.chatbot.amqp.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.uniguairaca.chatbot.dto.RabbitMessageDTO;
import com.uniguairaca.chatbot.dto.WhatsappMessageDTO;
import com.uniguairaca.chatbot.dto.response.QRCodeResponseDTO;
import com.uniguairaca.chatbot.service.ChatbotService;
import com.uniguairaca.chatbot.service.WhatsappBrokerGateway;

@Component
public class RabbitMQListener {
  private final ChatbotService chatbotService;
  private final WhatsappBrokerGateway whatsappBrokerGateway;

  public RabbitMQListener(ChatbotService chatbotService, WhatsappBrokerGateway whatsappBrokerGateway) {
    this.chatbotService = chatbotService;
    this.whatsappBrokerGateway = whatsappBrokerGateway;
  }

  @RabbitListener(queues = "${rabbitmq.queue.responses}")
  public void receiveMessage(RabbitMessageDTO<?> message) {
    System.out.println("Mensagem recebida do RabbitMQ: " + message.getType());

    switch (message.getType()) {
      case QR_CODE_RESPONSE:
        QRCodeResponseDTO qrCodeResponse = (QRCodeResponseDTO) message.getPayload();
        whatsappBrokerGateway.completeQRCodeRequest(qrCodeResponse, message.getCorrelationId());
        break;
      case WHATSAPP_MESSAGE_RECEIVED:
        WhatsappMessageDTO whatsappMessage = (WhatsappMessageDTO) message.getPayload();
        chatbotService.processWhatsappMessage(whatsappMessage);
        break;
      default:
        System.out.println("Tipo de mensagem RabbitMQ desconhecido: " + message.getType());
    }
  }
}
