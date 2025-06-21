package com.example.charlistservice.Services;

import com.example.charlistservice.Models.CharacterList;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CharacterListService {
    ResponseEntity<?> createCharacter(CharacterList character);
    ResponseEntity<?> getCharactersByUser(String userId);
    ResponseEntity<?> getFullCharactersByUser(String userId);
    ResponseEntity<?> getFullCharacterByName(String userId, String characterName);

    // Новые методы для обновления
    ResponseEntity<?> updateAbilities(String characterId, Map<String, Object> abilities);
    ResponseEntity<?> updateVitalStats(String characterId, Map<String, Object> vitalStats);
    ResponseEntity<?> updateField(String characterId, String fieldName, Map<String, Object> fieldData);
    ResponseEntity<?> updateBasicInfo(String characterId, Map<String, Object> basicInfo);
    ResponseEntity<?> updateSkills(String characterId, Map<String, Object> skills);
    ResponseEntity<?> updatePersonality(String characterId, Map<String, Object> personality);
    ResponseEntity<?> updateSpells(String characterId, Map<String, Object> spells);
    ResponseEntity<?> updateSubInfo(String characterId, Map<String, Object> subInfo);
}
