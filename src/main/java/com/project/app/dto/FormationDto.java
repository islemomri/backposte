package com.project.app.dto;

import java.time.LocalDate;
import java.util.List;

import com.project.app.model.SousTypeFormation;
import com.project.app.model.TypeFormation;

import lombok.Data;

@Data
public class FormationDto {
    private String titre;
    private String description;
    private TypeFormation typeFormation;
    private SousTypeFormation sousTypeFormation;
    private LocalDate dateDebutPrevue;
    private LocalDate dateFinPrevue;
    private Long responsableEvaluationId; 
    private String responsableEvaluationExterne;
    private List<Long> employeIds;
}

