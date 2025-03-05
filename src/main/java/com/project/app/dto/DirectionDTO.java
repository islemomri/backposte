package com.project.app.dto;

import java.util.List;
import lombok.Data;

@Data
public class DirectionDTO {
    private String nom_direction;
    private List<Long> siteIds;
}
