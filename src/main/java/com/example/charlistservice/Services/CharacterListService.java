package com.example.charlistservice.Services;

import com.example.charlistservice.Models.CharacterList;
import org.springframework.http.ResponseEntity;

public interface CharacterListService {

    ResponseEntity<?> createCharacter(CharacterList character);
    ResponseEntity<?> getCharactersByUser(String userId);
    ResponseEntity<?> getFullCharactersByUser(String userId);
}
