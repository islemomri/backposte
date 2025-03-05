package com.project.app.controller;

import com.project.app.dto.PosteDTO;
import com.project.app.model.Direction;
import com.project.app.model.Poste;
import com.project.app.service.IPosteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/recrutement/postes")
public class PosteController {

    @Autowired
    private IPosteService posteService;

    @PostMapping("/ajouter")
    public ResponseEntity<Poste> ajouterPoste(@RequestBody PosteDTO posteDTO) {
        Poste poste = posteService.addPosteWithDirections(posteDTO);
        return new ResponseEntity<>(poste, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Poste> getAllPostes() {
        return posteService.getAllPostes();
    }
    
    @GetMapping("/{id}")
    public Poste getPosteById(@PathVariable("id") Long id) {
        return posteService.getPosteById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Poste> updatePoste(
            @PathVariable Long id,
            @RequestBody PosteDTO posteDto) {
        
        Poste updatedPoste = posteService.updatePoste(id, posteDto);
        
        return ResponseEntity.ok(updatedPoste);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePoste(@PathVariable("id") Long id) {
        posteService.deletePoste(id);
        return ResponseEntity.ok("Poste supprimé avec succès !");
    }
    
    @GetMapping("getAllPostesnonArchivés")
    public List<Poste> getAllPostesnonArchivés() {
        return posteService.getAllPostesnonArchivés();
    }

    @PutMapping("/{id}/archiver")
    public Poste archiverPoste(@PathVariable Long id) {
        return posteService.archiverPoste(id);
    }

    @GetMapping("/liste-Postes-archivés")
    public List<Poste> getAllPostesArchivés() {
        return posteService.getAllPostesArchivés();
    }

    @PutMapping("/{id}/desarchiver")
    public Poste desarchiverDirection(@PathVariable Long id) {
        return posteService.desarchiverPoste(id);
    }
    @GetMapping("/{id}/directions")
    public ResponseEntity<Set<Direction>> getDirectionsByPosteId(@PathVariable Long id) {
        return posteService.getDirectionsByPosteId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    
}
