package by.grechishnikov.messenger.contact.service;

import by.grechishnikov.messenger.contact.dto.ContactDTO;
import by.grechishnikov.messenger.contact.entity.Contact;
import by.grechishnikov.messenger.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class ContactServiceImpl implements ContactService{

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void saveContact(ContactDTO contact) {
        contactRepository.save(new Contact(contact));
    }

    @Override
    public void deleteById(int contactId) {
        contactRepository.deleteById(contactId);
    }

    @Override
    public Set<Contact> findAllContactsByUserId(int userId) {
        return contactRepository.findAllByUserId(userId);
    }
}
