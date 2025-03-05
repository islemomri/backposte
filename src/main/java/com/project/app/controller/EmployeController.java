package com.project.app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.dto.EmployeDTO;
import com.project.app.dto.EmployePosteRequest;
import com.project.app.dto.EmployeUpdateDTO;
import com.project.app.dto.PosteAvecDatesDTO;
import com.project.app.model.Discipline;
import com.project.app.model.Employe;
import com.project.app.model.EmployePoste;
import com.project.app.model.Experience;
import com.project.app.model.ExperienceAnterieure;
import com.project.app.model.ExperienceAssad;
import com.project.app.model.Poste;
import com.project.app.service.IEmployeService;

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/employes")
public class EmployeController {
	@Autowired
    private IEmployeService employeService;

    @GetMapping
    public List<Employe> getAllEmployes() {
        return employeService.getAllEmployes();
    }

    @PostMapping
    public Employe addEmploye(@RequestBody Employe employe) {
        return employeService.addEmploye(employe);
    }

    @GetMapping("/{id}")
    public Optional<Employe> getEmployeById(@PathVariable Long id) {
        return employeService.getEmployeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEmploye(@PathVariable Long id) {
        employeService.deleteEmploye(id);
    }
    
    
    @PostMapping("/addWithExperience")
    public ResponseEntity<Employe> addEmployeWithExperience(@RequestBody Employe employe) {
        Employe savedEmploye = employeService.addEmployeWithExperience(employe);
        return new ResponseEntity<>(savedEmploye, HttpStatus.CREATED);
    }
  

    @PostMapping("/test")
    public ResponseEntity<Employe> ajouterEmploye(@RequestBody EmployeDTO employeDTO) {
        Employe nouvelEmploye = employeService.ajouterEmploye(employeDTO);
        return ResponseEntity.ok(nouvelEmploye);
    }
    

 
    
    @GetMapping("/{id}/disciplines")
    public List<Discipline> getAllDisciplines(@PathVariable Long id) {
        return employeService.getAllDisciplines(id);
    }
    
    @GetMapping("/employes/{id}/experiences/assad")
    public List<ExperienceAssad> getAllExperiencesAssadByEmployeId(@PathVariable Long id) {
        return employeService.getAllExperiencesAssadByEmployeId(id);
    }

    // API pour obtenir la liste des expériences antérieures d'un employé par son ID
    @GetMapping("/employes/{id}/experiences/anterieures")
    public List<ExperienceAnterieure> getAllExperiencesAnterieuresByEmployeId(@PathVariable Long id) {
        return employeService.getAllExperiencesAnterieuresByEmployeId(id);
    }
    
    
    
    @PostMapping("/ajouterAvecPoste")
    public ResponseEntity<PosteAvecDatesDTO> ajouterPosteAEmploye(
            @RequestParam Long employeId,
            @RequestParam Long posteId,
            @RequestParam Long directionId,
            @RequestParam Long siteId,
            @RequestParam LocalDate dateDebut,
            @RequestParam(required = false) LocalDate dateFin) {  // dateFin est maintenant optionnelle

        try {
            // Appel du service pour ajouter le poste à l'employé et obtenir le DTO
            PosteAvecDatesDTO posteAvecDatesDTO = employeService.ajouterPosteAEmploye(employeId, posteId, directionId, siteId, dateDebut, dateFin);

            // Retourner une réponse avec le DTO du poste
            return ResponseEntity.ok(posteAvecDatesDTO);
        } catch (RuntimeException e) {
            // Gérer les exceptions si l'employé, le poste, la direction ou le site n'existent pas
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<EmployePoste> getPosteDetails(
            @RequestParam("employeId") Long employeId,
            @RequestParam("posteId") Long posteId) {

        Optional<EmployePoste> employePoste = employeService.getPosteDetailsByEmployeIdAndPosteId(employeId, posteId);
        
        return employePoste
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/postes/{employeId}")
    public ResponseEntity<List<PosteAvecDatesDTO>> getPostesByEmploye(@PathVariable Long employeId) {
        try {
            List<PosteAvecDatesDTO> postesAvecDates = employeService.getPostesByEmploye(employeId);
            return ResponseEntity.ok(postesAvecDates);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping("/modifierPosteemploye")
    public ResponseEntity<PosteAvecDatesDTO> modifierPosteAEmploye(
            @RequestParam Long employeId,
            @RequestParam Long posteId,
            @RequestParam Long directionId,
            @RequestParam Long siteId,
            @RequestParam LocalDate dateDebut,
            @RequestParam(required = false) LocalDate dateFin) {  // DateFin peut être nulle

        try {
            // Appel du service pour modifier le poste de l'employé
            PosteAvecDatesDTO posteAvecDatesDTO = employeService.modifierPosteAEmploye(employeId, posteId, directionId, siteId, dateDebut, dateFin);

            // Retourner une réponse avec le DTO mis à jour
            return ResponseEntity.ok(posteAvecDatesDTO);
        } catch (RuntimeException e) {
            // Gérer les erreurs si quelque chose échoue
            return ResponseEntity.badRequest().body(null);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> supprimerPoste(@RequestParam Long employeId, @RequestParam Long posteId) {
    	employeService.supprimerPostePourEmploye(employeId, posteId);
        return ResponseEntity.ok("Poste supprimé avec succès !");
    }
    
    @PostMapping("/ajouter")
    public Employe ajouterEmployeAvecPoste(@RequestParam Long posteId,
                                           @RequestParam Long directionId,
                                           @RequestParam Long siteId,
                                           @RequestBody Employe employe,
                                           @RequestParam String dateDebut,
                                           @RequestParam String dateFin) {
        // Convertir les dates
        LocalDate dateDebutLocal = LocalDate.parse(dateDebut);
        LocalDate dateFinLocal = LocalDate.parse(dateFin);

        // Ajouter l'employé avec son poste
        return employeService.ajouterEmployeAvecPoste(posteId, directionId, siteId, employe, dateDebutLocal, dateFinLocal);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Employe> modifierEmploye(
            @PathVariable Long id,
            @RequestBody Employe employe,
            @RequestParam(required = false) Long posteId,
            @RequestParam(required = false) Long directionId,
            @RequestParam(required = false) Long siteId,
            @RequestParam(required = false) LocalDate dateDebut,
            @RequestParam(required = false) LocalDate dateFin) {
        
        Employe updatedEmploye = employeService.modifierEmploye(id, employe, posteId, directionId, siteId, dateDebut, dateFin);
        return ResponseEntity.ok(updatedEmploye);
    }
    @GetMapping("/employes-without-poste")
    public List<Employe> getEmployesWithoutPoste() {
        return employeService.getEmployesWithoutPoste();
    }
}
