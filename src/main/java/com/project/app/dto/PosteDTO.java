package com.project.app.dto;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
public class PosteDTO {
	 private String titre;
	    private String niveauExperience;
	    private String diplomeRequis;
	    private String competencesRequises;
	    private Set<Long> directionIds;
	    @Schema(type = "string", format = "binary")
	    private MultipartFile document;

}