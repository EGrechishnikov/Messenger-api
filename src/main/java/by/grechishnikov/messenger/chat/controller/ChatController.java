package by.grechishnikov.messenger.chat.controller;

import by.grechishnikov.messenger.chat.entity.Chat;
import by.grechishnikov.messenger.chat.service.ChatService;
import by.grechishnikov.messenger.message.entity.Message;
import by.grechishnikov.messenger.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author - Evgeniy Grechishnikov
 */
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private ChatService chatService;
    private MessageService messageService;

    @Autowired
    public ChatController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Chat chat) {
        try {
            chatService.saveOrUpdate(chat);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Chat>> findChatsByUserId(@PathVariable int userId) {
        try {
            return new ResponseEntity<>(chatService.findAllByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<Page<Message>> findMessagesByChatId(@PathVariable int chatId, Pageable pageable) {
        try {
            return new ResponseEntity<>(messageService.findAllByChatId(chatId, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
