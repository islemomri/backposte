package com.project.app.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.project.app.model.Permission;

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
    private LocalDateTime lastLogin;
    private Set<Permission> permissions;

}
