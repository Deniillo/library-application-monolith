package itmo.deniill.service.dto.graphql;

import lombok.Data;

@Data
public class NewsUpdateInput {
    private int id;
    private String headline;
    private String description;
    private String photoUrl;
}
