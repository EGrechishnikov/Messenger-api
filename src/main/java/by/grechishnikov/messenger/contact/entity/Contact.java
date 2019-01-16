package by.grechishnikov.messenger.contact.entity;

import by.grechishnikov.messenger.common.entity.AbstractEntity;
import by.grechishnikov.messenger.contact.dto.ContactDTO;
import by.grechishnikov.messenger.user.entity.User;
import lombok.*;

import javax.persistence.*;

/**
 * @author - Evgeniy Grechishnikov
 */
@Entity
@Table(name = "t_contact")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Contact extends AbstractEntity {

    @Column(name = "user_id", nullable = false)
    private int userId;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "contact_id", nullable = false)
    private User contact;

    public Contact(ContactDTO contactDTO) {
        this.userId = contactDTO.getUserId();
        this.contact = new User();
        contact.setId(contactDTO.getContactId());
    }
}
