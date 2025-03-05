package com.project.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.dto.FormationDto;
import com.project.app.model.Formation;
import com.project.app.service.FormationService;

@RestController
@RequestMapping("/formations")
public class FormationController {

    private final FormationService formationService;

    @Autowired
    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    @PostMapping("/{rhId}/creer")
    public ResponseEntity<?> creerFormation(@RequestBody FormationDto formationDto, @PathVariable("rhId") Long rhId) {
        Formation formationCree = formationService.creerFormation(formationDto, rhId);
        return ResponseEntity.status(HttpStatus.CREATED).body(formationCree);
    }


    @GetMapping("/{rhId}")
    public ResponseEntity<List<Formation>> getFormationsParRH(@PathVariable("rhId") Long rhId) {
        return ResponseEntity.ok(formationService.getFormationsParRH(rhId));
    }
    
    @GetMapping("/employe/{employeId}")
    public ResponseEntity<List<Formation>> getFormationsParEmploye(@PathVariable("employeId") Long employeId) {
        List<Formation> formations = formationService.getFormationsParEmploye(employeId);
        return ResponseEntity.ok(formations);
    }

}
