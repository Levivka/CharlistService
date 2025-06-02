package com.example.charlistservice.Controllers;

import com.example.charlistservice.Services.CharacterListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/charlist")
public class CharacterListController {
    private final CharacterListService characterListService;

    @PostMapping()
    public ResponseEntity<?> createCharacter(@RequestBody Character character) {
        return characterListService.createCharacter(character);
    }
}
