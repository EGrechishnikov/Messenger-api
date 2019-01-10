package by.grechishnikov.messenger.attachment.entity;

import by.grechishnikov.messenger.common.entity.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author - Evgeniy Grechishnikov
 */
@Entity
@Table(name = "t_attachment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attachment extends AbstractEntity {

    @Column
    private byte[] content;

}
