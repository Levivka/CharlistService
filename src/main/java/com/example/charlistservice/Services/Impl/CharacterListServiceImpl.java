package com.example.charlistservice.Services.Impl;

import com.example.charlistservice.Repositories.CharacterListRepository;
import com.example.charlistservice.Services.CharacterListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterListServiceImpl implements CharacterListService {
    private final CharacterListRepository characterRepository;

    @Override
    public ResponseEntity<?> createCharacter(Character character) {
        return ResponseEntity.ok(characterRepository.save(character));
    }
}
