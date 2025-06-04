package com.example.charlistservice.Services.Impl;

import com.example.charlistservice.Models.CharacterList;
import com.example.charlistservice.Models.DTO.CharacterListResponseDto;
import com.example.charlistservice.Repositories.CharacterListRepository;
import com.example.charlistservice.Services.CharacterListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterListServiceImpl implements CharacterListService {
    private final CharacterListRepository characterRepository;

    @Override
    public ResponseEntity<?> createCharacter(CharacterList character) {
        return ResponseEntity.ok(characterRepository.save(character));
    }

    public ResponseEntity<?> getCharactersByUser(String userId) {
        List<CharacterList> characters = characterRepository.findByUserId(userId);
        return ResponseEntity.ok(characters.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> getFullCharactersByUser(String userId) {
        List<CharacterList> characters = characterRepository.findByUserId(userId);
        return ResponseEntity.ok(characters);
    }


    private CharacterListResponseDto convertToDto(CharacterList character) {
        CharacterListResponseDto dto = new CharacterListResponseDto();
        dto.setId(character.getId());
        dto.setName(character.getBasicInfo().getCharacterName());
        dto.setLevel(character.getBasicInfo().getCharacterLevel());
        dto.setRace(character.getBasicInfo().getCharacterRace());
        return dto;
    }
}
