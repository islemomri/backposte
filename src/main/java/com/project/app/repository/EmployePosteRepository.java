package com.project.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.app.dto.PosteAvecDatesDTO;
import com.project.app.model.Employe;
import com.project.app.model.EmployePoste;
import com.project.app.model.Poste;

import jakarta.transaction.Transactional;
@Repository
public interface EmployePosteRepository extends JpaRepository<EmployePoste, Long>{



    @Query("SELECT ep FROM EmployePoste ep WHERE ep.employe.id = :employeId AND ep.poste.id = :posteId")
    Optional<EmployePoste> findPosteDetailsByEmployeIdAndPosteId(@Param("employeId") Long employeId, @Param("posteId") Long posteId);
    
    
    
    
    @Transactional
    void deleteByEmployeIdAndPosteId(Long employeId, Long posteId);
    
    
    @Query("SELECT ep FROM EmployePoste ep JOIN FETCH ep.poste WHERE ep.employe.id = :employeId")
    List<EmployePoste> findByEmployeIdWithPoste(@Param("employeId") Long employeId);
    
	}


