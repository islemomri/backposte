package com.project.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.dto.MessageDto;
import com.project.app.model.Message;
import com.project.app.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/envoyer")
    public ResponseEntity<?> envoyerMessage(@RequestBody MessageDto messageDto) {
        System.out.println("Données reçues dans le backend: " + messageDto);
        if (messageDto.getExpediteurId() == null || messageDto.getDestinataireId() == null) {
            return ResponseEntity.badRequest().body("Expéditeur et destinataire doivent être spécifiés");
        }
        Message message = messageService.envoyerMessage(
                messageDto.getExpediteurId(),
                messageDto.getDestinataireId(),
                messageDto.getSujet(),
                messageDto.getContenu(),
                messageDto.getMessageParentId());
        return ResponseEntity.ok(message);
    }



    @GetMapping("/recus/{userId}")
    public ResponseEntity<List<Message>> getMessagesRecus(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getMessagesRecus(userId));
    }

    @GetMapping("/envoyes/{userId}")
    public ResponseEntity<List<Message>> getMessagesEnvoyes(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getMessagesEnvoyes(userId));
    }

    @PutMapping("/lu/{messageId}")
    public ResponseEntity<?> marquerCommeLu(@PathVariable Long messageId) {
        messageService.marquerCommeLu(messageId);
        return ResponseEntity.ok("Message marqué comme lu");
    }
    
    @GetMapping("/thread/{messageId}")
    public ResponseEntity<List<Message>> getFilDiscussion(@PathVariable Long messageId) {
        return ResponseEntity.ok(messageService.getFilDiscussion(messageId));
    }
    
    @PostMapping("/repondre")
    public ResponseEntity<?> repondreMessage(@RequestBody MessageDto messageDto) {
        if (messageDto.getExpediteurId()	 == null || messageDto.getDestinataireId() == null || messageDto.getMessageParentId() == null) {
            return ResponseEntity.badRequest().body("Expéditeur, destinataire et message parent doivent être spécifiés");
        }
        Message message = messageService.repondreMessage(
                messageDto.getExpediteurId(),
                messageDto.getDestinataireId(),
                messageDto.getSujet(),
                messageDto.getContenu(),
                messageDto.getMessageParentId());
        return ResponseEntity.ok(message);
    }



}

