package itmo.deniill.service.dto.graphql;

import itmo.deniill.dao.model.enums.NewsType;
import lombok.Data;

@Data
public class NewsUpdateInput {
    private Integer id;
    private String headline;
    private String description;
    private String photoUrl;
    private NewsType type;
}
