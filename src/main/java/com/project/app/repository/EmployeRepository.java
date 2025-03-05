package com.project.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.app.model.Employe;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
	 Optional<Employe> findByMatricule(Long matricule);
	 @Query("SELECT e FROM Employe e WHERE e.ajout = false")
	 List<Employe> findEmployesWhereAjoutIsFalse();
	 
	 
	 @Query("SELECT e FROM Employe e WHERE e.ajout = true")
	 List<Employe> findEmployesWhereAjoutIsTrue();

}
