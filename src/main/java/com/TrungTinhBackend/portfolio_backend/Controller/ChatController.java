package com.TrungTinhBackend.portfolio_backend.Controller;

import com.TrungTinhBackend.portfolio_backend.Entity.Message;
import com.TrungTinhBackend.portfolio_backend.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    // WebSocket: Nhận và phát tin nhắn
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        message.setTimestamp(java.time.LocalDateTime.now());
        return messageRepository.save(message); // Lưu tin nhắn vào DB
    }

    // REST API: Lấy lịch sử tin nhắn
    @GetMapping
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }
}
