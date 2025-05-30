package com.uniguairaca.chatbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uniguairaca.chatbot.dto.request.QRCodeRequestDTO;
import com.uniguairaca.chatbot.dto.response.QRCodeResponseDTO;
import com.uniguairaca.chatbot.service.WhatsappBrokerGateway;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/chatbot")
public class ChatbotController {
    private final WhatsappBrokerGateway whatsappBrokerGateway;

    public ChatbotController(WhatsappBrokerGateway whatsappBrokerGateway) {
        this.whatsappBrokerGateway = whatsappBrokerGateway;
    }

    @PostMapping("/qrcode/request")
    public ResponseEntity<QRCodeResponseDTO> requestQRCode(@RequestBody(required = false) QRCodeRequestDTO requestDTO) {
        if (requestDTO == null) {
            // TODO: Get microsservice url
            String callbackUrl = ""
            requestDTO = new QRCodeRequestDTO("my-chatbot-session-" + System.currentTimeMillis(), callbackUrl);
        }

        try {
            QRCodeResponseDTO qrCodeResponse = whatsappBrokerGateway.requestQRCode(requestDTO).get();
            return ResponseEntity.ok(qrCodeResponse);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500)
                    .body(new QRCodeResponseDTO(null, null, "ERROR", "Falha ao obter QR Code: " + e.getMessage()));
        }
    }
}
