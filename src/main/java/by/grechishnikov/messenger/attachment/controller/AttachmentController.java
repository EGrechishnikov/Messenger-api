package by.grechishnikov.messenger.attachment.controller;

import by.grechishnikov.messenger.attachment.entity.Attachment;
import by.grechishnikov.messenger.attachment.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author - Evgeniy Grechishnikov
 */
@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Attachment attachment) {
        try {
            attachmentService.saveOrUpdate(attachment);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attachment> findById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(attachmentService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
