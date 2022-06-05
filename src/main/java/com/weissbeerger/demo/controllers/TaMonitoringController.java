package com.weissbeerger.demo.controllers;

import com.weissbeerger.demo.dao.InMemoryStorage;
import com.weissbeerger.demo.model.taModel.MessagesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaMonitoringController {
    @Autowired
    InMemoryStorage inMemoryStorage;

    @GetMapping("/messages")
    public ResponseEntity<MessagesDTO> getMessages(){
        MessagesDTO msgs = MessagesDTO.builder().messages(inMemoryStorage.getAll()).build();
        return new ResponseEntity(msgs, HttpStatus.OK);
    }
}
