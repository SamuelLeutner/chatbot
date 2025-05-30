package com.uniguairaca.chatbot.amqp.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.uniguairaca.chatbot.dto.RabbitMessageDTO;

@Service
public class RabbitMQSender {

  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.queue.requests}")
  private String requestQueue;

  public RabbitMQSender(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void send(RabbitMessageDTO<?> message) {
    System.out.println("Enviando mensagem para RabbitMQ: " + message.getType());
    rabbitTemplate.convertAndSend(requestQueue, message);
  }
}