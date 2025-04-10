package com.project.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
@Data
@Entity
public class Employe {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nom;
	    private String prenom;
	  
	   
	    private Integer matricule;

	    private boolean actif;
	    private LocalDate dateNaissance;
	    private LocalDate dateRecrutement;
	    private String sexe;
	    private String email;
	private boolean ajout;
	
	    private String photo;
	   @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
	   @JsonIgnore
	    private List<Discipline> disciplines = new ArrayList<>(); 
	    
	   @JsonIgnore
	    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Experience> experiences = new ArrayList<>();
	   
		
	    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonIgnore
	    private List<Stage> stages = new ArrayList<>(); 
	    
	    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonIgnore
	    private List<EmployePoste> employePostes = new ArrayList<>();
	    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonIgnore
	    private List<Diplome> diplomes = new ArrayList<>();
	    
	    @ManyToMany(mappedBy = "employes")
	    @JsonIgnore
	    private List<Formation> formations = new ArrayList<>();
	    
	    
	    
	 
	    
}
