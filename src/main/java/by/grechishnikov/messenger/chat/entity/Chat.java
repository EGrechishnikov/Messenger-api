package by.grechishnikov.messenger.chat.entity;

import by.grechishnikov.messenger.common.entity.AbstractEntity;
import by.grechishnikov.messenger.message.entity.Message;
import by.grechishnikov.messenger.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author - Evgeniy Grechishnikov
 */
@Entity
@Table(name = "t_chat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = "lastMessage")
@EqualsAndHashCode(callSuper = true, exclude = "lastMessage")
public class Chat extends AbstractEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_chat_user",
            joinColumns = {@JoinColumn(name = "chat_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users = new HashSet<>();

    @Transient
    private Message lastMessage;

}
