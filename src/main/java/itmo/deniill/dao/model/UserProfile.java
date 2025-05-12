package itmo.deniill.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserProfile {
    @Id
    private Long id;
    private String first_name;
    private String last_name;
    private String photo_url;
    private String address;
    private String life_status;
    private Date birthday;
    private Date last_online;
}
