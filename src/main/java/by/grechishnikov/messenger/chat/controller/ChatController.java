package by.grechishnikov.messenger.chat.controller;

import by.grechishnikov.messenger.chat.entity.Chat;
import by.grechishnikov.messenger.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
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
    public ResponseEntity<List<Chat>> findContactsByUserId(@PathVariable int userId) {
        try {
            return new ResponseEntity<>(chatService.findAllByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
