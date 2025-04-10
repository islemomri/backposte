package com.project.app.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.app.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	Optional<Utilisateur> findByUsername(String username);
	Boolean existsByUsername(String username);
	Optional<Utilisateur> findByEmail(String email);
    Boolean existsByEmail(String email);
    
    @Modifying
    @Query(value = """
        UPDATE utilisateur u SET u.last_login = :date WHERE u.username = :username;
        UPDATE rh r SET r.last_login = :date WHERE r.username = :username;
        UPDATE administrateur a SET a.last_login = :date WHERE a.username = :username;
        UPDATE responsable r SET r.last_login = :date WHERE r.username = :username;
        UPDATE directeur d SET d.last_login = :date WHERE d.username = :username;
        """, 
        nativeQuery = true)
    void updateLastLogin(@Param("username") String username, 
                       @Param("date") LocalDateTime date);
    
}
