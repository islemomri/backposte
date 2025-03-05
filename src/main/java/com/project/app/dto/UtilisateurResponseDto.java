package com.project.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UtilisateurResponseDto {
	private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String username;
    private String role;

}
