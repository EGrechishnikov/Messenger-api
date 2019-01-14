package by.grechishnikov.messenger.user.entity;

import by.grechishnikov.messenger.attachment.entity.Attachment;
import by.grechishnikov.messenger.common.entity.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author - Evgeniy Grechishnikov
 */
@Entity
@Table(name = "t_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "password", callSuper = true)
@ToString(callSuper = true, exclude = "password")
public class User extends AbstractEntity {

    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column
    private String name;
    @Column
    private int failedLoginAttempts;
    @Column
    private Date blockedTill;
    @Column
    private String currentRefreshToken;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id")
    private Attachment attachment;
    @Transient
    @ManyToMany
    @JoinTable(name = "t_contacts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private List<User> contacts;

    public void setBlocked() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.HOUR, 24);
        setBlockedTill(calendar.getTime());
        setFailedLoginAttempts(0);
        setCurrentRefreshToken("");
    }

    public boolean isBlocked() {
        return getBlockedTill() != null && new Date().before(getBlockedTill());
    }

}
