package itmo.deniill.dao.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Genre {
    @Schema(description = "Классическая литература") CLASSIC,
    @Schema(description = "Фантастика и фэнтези") FANTASY,
    @Schema(description = "Научная литература") SCIENCE,
    @Schema(description = "Детективы и триллеры") DETECTIVE
}
