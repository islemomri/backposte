package com.project.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeFormation typeFormation; 

    @Enumerated(EnumType.STRING)
    private SousTypeFormation sousTypeFormation;

    private LocalDate dateDebutPrevue;
    private LocalDate dateFinPrevue;
    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;

    @ManyToOne
    @JsonIgnore
    private RH organisateur;

    @ManyToOne
    @JsonIgnore
    private Utilisateur responsableEvaluation; 

    private String responsableEvaluationExterne; 
    
    @ManyToMany
    @JoinTable(
        name = "formation_employe",
        joinColumns = @JoinColumn(name = "formation_id"),
        inverseJoinColumns = @JoinColumn(name = "employe_id")
    )
    private List<Employe> employes = new ArrayList<>();
    
    
}
