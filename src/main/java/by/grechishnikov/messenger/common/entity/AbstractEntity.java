package by.grechishnikov.messenger.common.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author - Evgeniy Grechishnikov
 */
@MappedSuperclass
@EqualsAndHashCode(exclude = {"created", "updated"})
@Getter
@Setter
@ToString
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;
    @Column(nullable = false)
    private Date created = new Date();
    @Column(nullable = false)
    private Date updated = new Date();

    public void setUpdated() {
        this.updated = new Date();
    }

}
