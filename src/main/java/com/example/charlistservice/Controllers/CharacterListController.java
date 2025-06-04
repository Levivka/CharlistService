package com.example.charlistservice.Controllers;

import com.example.charlistservice.Models.CharacterList;
import com.example.charlistservice.Services.CharacterListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/charlist")
public class CharacterListController {
    private final CharacterListService characterListService;

    @PostMapping()
    public ResponseEntity<?> createCharacter(@RequestBody CharacterList character) {
        return characterListService.createCharacter(character);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCharactersMainInfo(@RequestParam String userId) {
        return characterListService.getCharactersByUser(userId);
    }

    @GetMapping
    public ResponseEntity<?> getFullCharacters(@RequestParam String userId) {
        return characterListService.getFullCharactersByUser(userId);
    }
}
