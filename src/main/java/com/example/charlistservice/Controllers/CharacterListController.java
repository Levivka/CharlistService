package com.example.charlistservice.Controllers;

import com.example.charlistservice.Models.CharacterList;
import com.example.charlistservice.Services.CharacterListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/user/character")
    public ResponseEntity<?> getFullCharacterByName(@RequestParam String userId, @RequestParam String characterName) {
        return characterListService.getFullCharacterByName(userId, characterName);
    }
    @PatchMapping("/{characterId}/basic-info")
    public ResponseEntity<?> updateBasicInfo(
            @PathVariable String characterId,
            @RequestBody Map<String, Object> request) {
        try {
            log.info("Updating basic info for character: {}", characterId);
            log.info("data: {}", request);


            if (!request.containsKey("basicInfo")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Missing 'basicInfo' in request"));
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> basicInfo = (Map<String, Object>) request.get("basicInfo");

            // Валидация обязательных полей
            if (!basicInfo.containsKey("characterName") ||
                    !basicInfo.containsKey("characterClass") ||
                    !basicInfo.containsKey("characterRace") ||
                    !basicInfo.containsKey("characterLevel")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Missing required fields in basicInfo"));
            }

            return characterListService.updateBasicInfo(characterId, basicInfo);
        } catch (Exception e) {
            log.error("Error updating basic info: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{characterId}/skills")
    public ResponseEntity<?> updateSkills(
            @PathVariable String characterId,
            @RequestBody Map<String, Object> skills) {
        return characterListService.updateSkills(characterId, skills);
    }

    @PatchMapping("/{characterId}/personality")
    public ResponseEntity<?> updatePersonality(
            @PathVariable String characterId,
            @RequestBody Map<String, Object> personality) {
        return characterListService.updatePersonality(characterId, personality);
    }

    @PatchMapping("/{characterId}/spells")
    public ResponseEntity<?> updateSpells(
            @PathVariable String characterId,
            @RequestBody Map<String, Object> spells) {
        return characterListService.updateSpells(characterId, spells);
    }

    @PatchMapping("/{characterId}/sub-info")
    public ResponseEntity<?> updateSubInfo(
            @PathVariable String characterId,
            @RequestBody Map<String, Object> subInfo) {
        return characterListService.updateSubInfo(characterId, subInfo);
    }

    @PatchMapping("/{characterId}/vital-stats")
    public ResponseEntity<?> updateVitalStats(
            @PathVariable String characterId,
            @RequestBody Map<String, Object> vitalStats) {
        try {
            log.debug("Updating vital stats for character {}: {}", characterId, vitalStats);
            return characterListService.updateVitalStats(characterId, vitalStats);
        } catch (Exception e) {
            log.error("Error updating vital stats for character {}: {}", characterId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/{characterId}/abilities")
    public ResponseEntity<?> updateAbilities(
            @PathVariable String characterId,
            @RequestBody Map<String, Object> abilities) {
        try {
            log.debug("Updating abilities for character {}: {}", characterId, abilities);
            return characterListService.updateAbilities(characterId, abilities);
        } catch (Exception e) {
            log.error("Error updating abilities for character {}: {}", characterId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/{characterId}/field")
    public ResponseEntity<?> updateField(
            @PathVariable String characterId,
            @RequestBody Map<String, Object> fieldData) {  // Убираем @RequestParam fieldName
        try {
            // fieldName теперь должен приходить в теле запроса
            if (!fieldData.containsKey("fieldName")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "fieldName is required in request body"));
            }
            String fieldName = (String) fieldData.remove("fieldName");  // Извлекаем и удаляем из map

            log.debug("Updating field {} for character {}: {}", fieldName, characterId, fieldData);
            return characterListService.updateField(characterId, fieldName, fieldData);
        } catch (Exception e) {
            log.error("Error updating field for character {}: {}", characterId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
