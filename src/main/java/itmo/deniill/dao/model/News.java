package itmo.deniill.dao.model;

import itmo.deniill.dao.model.enums.NewsType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String headline;
    private String description;
    @Column(name = "photo_url")
    private String photoUrl;
    @Enumerated(EnumType.STRING)
    private NewsType type;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
