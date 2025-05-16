package itmo.deniill.service.dto.graphql;

import lombok.Data;

@Data
public class NewsInput {
    private String headline;
    private String description;
    private String photoUrl;
}
