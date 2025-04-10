package com.project.app.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Long id;
    private String sujet;
    private String contenu;
    private Long expediteurId;
    private String expediteurNom;
    private String expediteurPrenom;
    private Long destinataireId;
    private String destinataireNom;
    private String destinatairePrenom;
    private boolean lu;
    private Long messageParentId;
    
    // Getters et Setters
}