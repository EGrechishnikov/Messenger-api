package by.grechishnikov.messenger.user.entity;

import by.grechishnikov.messenger.attachment.entity.Attachment;
import by.grechishnikov.messenger.common.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
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

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setBlocked() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.HOUR, 24);
        setBlockedTill(calendar.getTime());
        setFailedLoginAttempts(0);
        setCurrentRefreshToken("");
    }

    @JsonIgnore
    public boolean isBlocked() {
        return getBlockedTill() != null && new Date().before(getBlockedTill());
    }

}
