package com.uniguairaca.chatbot.service;

import org.springframework.stereotype.Service;

import com.uniguairaca.chatbot.dto.WhatsappMessageDTO;
import com.uniguairaca.chatbot.dto.response.ChatbotResponseDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ChatbotService {
  private final WhatsappBrokerGateway whatsappBrokerGateway;
  private final Map<String, ChatState> userStates = new HashMap<>();
  private final Map<String, ConversationStep> conversationFlow = new HashMap<>();

  private static class ConversationStep {
    String question;
    String nextStepKey;
    Map<String, String> responses;

    public ConversationStep(String question, Map<String, String> responses, String nextStepKey) {
      this.question = question;
      this.responses = responses;
      this.nextStepKey = nextStepKey;
    }
  }

  private static class ChatState {
    String currentStepKey;

    public ChatState(String currentStepKey) {
      this.currentStepKey = currentStepKey;
    }
  }

  public ChatbotService(WhatsappBrokerGateway whatsappBrokerGateway) {
    this.whatsappBrokerGateway = whatsappBrokerGateway;
    initializeConversationFlow();
  }

  private void initializeConversationFlow() {
    conversationFlow.put("start", new ConversationStep(
        "Olá! Bem-vindo(a) ao nosso chatbot. O que você gostaria de fazer?\n1. Ver opções\n2. Falar com atendente",
        Map.of("1", "options_menu", "2", "human_transfer"),
        null));

    conversationFlow.put("options_menu", new ConversationStep(
        "Temos as seguintes opções:\n1. Suporte\n2. Vendas\n3. Voltar ao início",
        Map.of(
            "1", "support_info",
            "2", "sales_contact",
            "3", "start"),
        null));

    conversationFlow.put("support_info", new ConversationStep(
        "Para suporte, visite nosso site em www.exemplo.com/suporte ou ligue para 0800-123-4567. Posso ajudar em mais alguma coisa?",
        Map.of("sim", "options_menu", "não", "end_chat"),
        "end_chat"

    ));

    conversationFlow.put("sales_contact", new ConversationStep(
        "Para vendas, envie um email para vendas@exemplo.com. Deseja retornar ao menu principal?",
        Map.of("sim", "options_menu", "não", "end_chat"),
        "end_chat"));

    conversationFlow.put("human_transfer", new ConversationStep(
        "Entendido. Encaminhando você para um de nossos atendentes. Aguarde um momento.",
        Map.of(),
        "end_chat"));

    conversationFlow.put("end_chat", new ConversationStep(
        "Agradecemos seu contato! Tenha um ótimo dia.",
        Map.of(),
        null));
  }

  public void processWhatsappMessage(WhatsappMessageDTO messageDTO) {
    String from = messageDTO.getFrom();
    String receivedText = messageDTO.getText().toLowerCase().trim();

    ChatState currentState = userStates.computeIfAbsent(from, k -> new ChatState("start"));

    String responseText;
    ConversationStep currentConversationStep = conversationFlow.get(currentState.currentStepKey);

    if (currentConversationStep == null) {
      responseText = "Ocorreu um erro no fluxo da conversa. Por favor, digite 'iniciar' para recomeçar.";
      currentState.currentStepKey = "start";
    } else {

      Optional<Map.Entry<String, String>> matchingResponse = currentConversationStep.responses.entrySet().stream()
          .filter(entry -> receivedText.contains(entry.getKey()))
          .findFirst();

      if (matchingResponse.isPresent()) {
        String nextStepKeyOrFinalResponse = matchingResponse.get().getValue();
        ConversationStep nextStep = conversationFlow.get(nextStepKeyOrFinalResponse);

        if (nextStep != null) {

          currentState.currentStepKey = nextStepKeyOrFinalResponse;
          responseText = nextStep.question;
        } else {

          responseText = nextStepKeyOrFinalResponse;
          currentState.currentStepKey = "end_chat";
        }
      } else {

        responseText = "Não entendi sua resposta. Por favor, escolha uma das opções ou reformule.\n"
            + currentConversationStep.question;
      }

      if (receivedText.equals("iniciar")) {
        currentState.currentStepKey = "start";
        responseText = conversationFlow.get("start").question;
      }
    }

    ChatbotResponseDTO response = new ChatbotResponseDTO(
        messageDTO.getSessionId(),
        messageDTO.getFrom(),
        responseText,
        messageDTO.getMessageId());
    whatsappBrokerGateway.sendChatbotResponse(response);
  }
}