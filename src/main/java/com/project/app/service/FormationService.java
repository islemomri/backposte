package com.project.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.app.dto.FormationDto;
import com.project.app.model.Employe;
import com.project.app.model.Formation;
import com.project.app.model.RH;
import com.project.app.model.Utilisateur;
import com.project.app.repository.EmployeRepository;
import com.project.app.repository.FormationRepository;
import com.project.app.repository.UtilisateurRepository;

import jakarta.transaction.Transactional;

@Service
public class FormationService implements IFormationService {

	private final FormationRepository formationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EmployeRepository employeRepository; // Ajoutez ce repository

    @Autowired
    private NotificationService notificationService;

    @Autowired
    public FormationService(FormationRepository formationRepository, UtilisateurRepository utilisateurRepository, EmployeRepository employeRepository) {
        this.formationRepository = formationRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.employeRepository = employeRepository;
    }

    @Override
    @Transactional
    public Formation creerFormation(FormationDto formationDto, Long rhId) {
        RH organisateur = (RH) utilisateurRepository.findById(rhId)
                .orElseThrow(() -> new UsernameNotFoundException("RH non trouvé"));

        Formation formation = new Formation();
        formation.setTitre(formationDto.getTitre());
        formation.setDescription(formationDto.getDescription());
        formation.setTypeFormation(formationDto.getTypeFormation());
        formation.setSousTypeFormation(formationDto.getSousTypeFormation());
        formation.setDateDebutPrevue(formationDto.getDateDebutPrevue());
        formation.setDateFinPrevue(formationDto.getDateFinPrevue());
        formation.setOrganisateur(organisateur);

        if (formationDto.getResponsableEvaluationId() != null) {
            Utilisateur responsable = utilisateurRepository.findById(formationDto.getResponsableEvaluationId())
                    .orElseThrow(() -> new UsernameNotFoundException("Responsable introuvable"));
            formation.setResponsableEvaluation(responsable);
            notificationService.creerNotification(responsable, 
                    "Vous avez été assigné comme responsable d'évaluation pour la formation : " + formation.getTitre());
        } else {
            formation.setResponsableEvaluationExterne(formationDto.getResponsableEvaluationExterne());
        }

        if (formationDto.getEmployeIds() != null && !formationDto.getEmployeIds().isEmpty()) {
            List<Employe> employes = employeRepository.findAllById(formationDto.getEmployeIds());
            formation.setEmployes(employes);
        }

        return formationRepository.save(formation);
    }


    @Override
    public List<Formation> getFormationsParRH(Long rhId) {
        return formationRepository.findByOrganisateurId(rhId);
    }
    
    public List<Formation> getFormationsParResponsable(Long responsableId) {
        return formationRepository.findByResponsableEvaluationId(responsableId);
    }
    
    public List<Formation> getFormationsParEmploye(Long employeId) {
        return formationRepository.findByEmployesId(employeId);
    }


}
