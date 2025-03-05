package com.project.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.app.model.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    List<Formation> findByOrganisateurId(Long rhId);
    List<Formation> findByResponsableEvaluationId(Long responsableId);
    List<Formation> findByEmployesId(Long employeId);

    List<Formation> findByOrganisateur_Id(Long rhId);
}
