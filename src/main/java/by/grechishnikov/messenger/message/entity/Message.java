package by.grechishnikov.messenger.message.entity;

import by.grechishnikov.messenger.common.entity.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author - Evgeniy Grechishnikov
 */
@Entity
@Table(name = "t_message")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Message extends AbstractEntity{

    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private int fromUserId;
    @Column(nullable = false)
    private int toUserId;

}
