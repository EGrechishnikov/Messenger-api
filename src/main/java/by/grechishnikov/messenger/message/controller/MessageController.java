package by.grechishnikov.messenger.message.controller;

import by.grechishnikov.messenger.message.entity.Message;
import by.grechishnikov.messenger.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author - Evgeniy Grechishnikov
 */
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Message message) {
        try {
            messageService.saveOrUpdate(message);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Page<Message>> findAllByChatId(@PathVariable int chatId, Pageable pageable) {
        try {
            return new ResponseEntity<>(messageService.findAllByChatId(chatId, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
