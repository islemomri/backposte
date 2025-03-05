package com.project.app.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.dto.PasswordResetRequest;
import com.project.app.dto.ResetPasswordRequest;
import com.project.app.dto.UtilisateurResponseDto;
import com.project.app.model.Administrateur;
import com.project.app.model.Directeur;
import com.project.app.model.RH;
import com.project.app.model.Responsable;
import com.project.app.model.Utilisateur;
import com.project.app.repository.UtilisateurRepository;
import com.project.app.service.AuthServiceImpl;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {
	
	private final UtilisateurRepository utilisateurRepository;
	private final AuthServiceImpl authServiceImpl;
	
	@Autowired
    public UtilisateurController(UtilisateurRepository utilisateurRepository, AuthServiceImpl authServiceImpl) {
        this.utilisateurRepository = utilisateurRepository;
        this.authServiceImpl = authServiceImpl;
    }
	
	@GetMapping
    public ResponseEntity<List<UtilisateurResponseDto>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        List<UtilisateurResponseDto> utilisateursDto = utilisateurs.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(utilisateursDto);
    }

    private UtilisateurResponseDto convertToDto(Utilisateur utilisateur) {
        String role = "";
        if (utilisateur instanceof Administrateur) {
            role = "ADMIN";
        } else if (utilisateur instanceof Directeur) {
            role = "DIRECTEUR";
        } else if (utilisateur instanceof RH) {
            role = "RH";
        } else if (utilisateur instanceof Responsable) {
            role = "RESPONSABLE";
        }

        return new UtilisateurResponseDto(utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getUsername(), role);
    }
    @GetMapping("/responsables")
    public ResponseEntity<List<UtilisateurResponseDto>> getResponsables() {
        // Filtrer les utilisateurs ayant le rôle Responsable
        List<Utilisateur> responsables = utilisateurRepository.findAll().stream()
                .filter(utilisateur -> utilisateur instanceof Responsable)
                .collect(Collectors.toList());

        // Convertir les utilisateurs filtrés en DTO
        List<UtilisateurResponseDto> responsablesDto = responsables.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responsablesDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUtilisateur(@PathVariable("id") Long id) {
        utilisateurRepository.deleteById(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponseDto> updateUtilisateur(
            @PathVariable Long id, 
            @RequestBody Utilisateur updatedUtilisateur) {
        
        return utilisateurRepository.findById(id)
                .map(utilisateur -> {
                    utilisateur.setNom(updatedUtilisateur.getNom());
                    utilisateur.setPrenom(updatedUtilisateur.getPrenom());
                    utilisateur.setEmail(updatedUtilisateur.getEmail());
                    utilisateur.setUsername(updatedUtilisateur.getUsername());
                    Utilisateur savedUser = utilisateurRepository.save(utilisateur);
                    return ResponseEntity.ok(convertToDto(savedUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<?> resetUserPassword(@PathVariable("id") Long id) {
        String newPassword = authServiceImpl.resetPassword(id);
        return ResponseEntity.ok(Map.of(
                "message", "Mot de passe réinitialisé avec succès",
                "newPassword", newPassword 
        ));
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        String response = authServiceImpl.resetPasswordWithToken(request.getToken(), request.getNewPassword(), request.getConfirmPassword());
        return ResponseEntity.ok(response);
    }




}
