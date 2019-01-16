package by.grechishnikov.messenger.contact.controller;

import by.grechishnikov.messenger.contact.dto.ContactDTO;
import by.grechishnikov.messenger.contact.entity.Contact;
import by.grechishnikov.messenger.contact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author - Evgeniy Grechishnikov
 */
@RestController
@RequestMapping("/contact")
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ContactDTO contact) {
        try {
            contactService.saveContact(contact);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Set<Contact>> findContactsByUserId(@PathVariable int userId) {
        try {
            return new ResponseEntity<>(contactService.findAllContactsByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity delete(@PathVariable int contactId) {
        try {
            contactService.deleteById(contactId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
