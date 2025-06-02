package com.example.charlistservice.Services;

import org.springframework.http.ResponseEntity;

public interface CharacterListService {

    ResponseEntity<?> createCharacter(Character character);
}
