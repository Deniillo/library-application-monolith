package itmo.deniill.dao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import itmo.deniill.dao.model.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор книги", example = "123")
    private Integer id;

    @Schema(description = "Название книги", example = "Война и мир", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Подробное описание книги", example = "Роман-эпопея Льва Толстого")
    private String description;

    @Schema(description = "Жанр произведения", example = "CLASSIC")
    private Genre genre;

    @Schema(description = "URL обложки книги", example = "books/book.jpg")
    @Column(name = "photo_url")
    private String photoUrl;
}
