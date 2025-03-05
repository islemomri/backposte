package com.project.app.dto;

import java.util.Set;

import lombok.Data;
@Data
public class PosteDTO {
	 private String titre;
	    private String niveauExperience;
	    private String diplomeRequis;
	    private String competencesRequises;
	    private Set<Long> directionIds;

}
