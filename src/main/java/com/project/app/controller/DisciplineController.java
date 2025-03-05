package com.project.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.app.model.Discipline;

import com.project.app.service.EmployeService;

import java.util.List;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {
    
   @Autowired
    private EmployeService employeService;

    
    @PostMapping("/{employeId}/disciplines")
    public ResponseEntity<Discipline> addDisciplineToEmploye(@PathVariable("employeId") Long employeId,@RequestBody Discipline discipline) {
        Discipline savedDiscipline = employeService.addDisciplineToEmploye(employeId, discipline);
        return ResponseEntity.ok(savedDiscipline);
    }

    @DeleteMapping("/{employeId}/disciplines/{disciplineId}")
    public void removeDisciplineFromEmploye(@PathVariable Long employeId, @PathVariable Long disciplineId) {
        employeService.removeDisciplineFromEmploye(employeId, disciplineId);
    }

    @PutMapping("/disciplines/{disciplineId}")
    public Discipline updateDiscipline(@PathVariable Long disciplineId, @RequestBody Discipline updatedDiscipline) {
        return employeService.updateDiscipline(disciplineId, updatedDiscipline);
    }

    @GetMapping("/{employeId}/disciplines")
    public List<Discipline> getDisciplinesByEmployeId(@PathVariable Long employeId) {
        return employeService.getDisciplinesByEmployeId(employeId);
    }
}
