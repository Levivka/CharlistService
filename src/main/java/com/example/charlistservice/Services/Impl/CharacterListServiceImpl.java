package com.example.charlistservice.Services.Impl;

import com.example.charlistservice.Models.CharacterList;
import com.example.charlistservice.Models.DTO.CharacterListResponseDto;
import com.example.charlistservice.Models.Pages.CharacterSpells;
import com.example.charlistservice.Models.SpellModifiers;
import com.example.charlistservice.Repositories.CharacterListRepository;
import com.example.charlistservice.Services.CharacterListService;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterListServiceImpl implements CharacterListService {
    private final CharacterListRepository characterRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<?> createCharacter(CharacterList character) {
        return ResponseEntity.ok(characterRepository.save(character));
    }

    @Override
    public ResponseEntity<?> getCharactersByUser(String userId) {
        try {
            List<CharacterList> characters = characterRepository.findByUserId(userId);

            // Обработка пустых заклинаний
            characters.forEach(character -> {
                if (character.getSpells() == null) {
                    character.setSpells(new CharacterSpells());
                }
            });

            return ResponseEntity.ok(characters.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("Error getting characters for user {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error getting characters: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getFullCharactersByUser(String userId) {
        List<CharacterList> characters = characterRepository.findByUserId(userId);
        return ResponseEntity.ok(characters);
    }

    @Override
    public ResponseEntity<?> getFullCharacterByName(String userId, String characterName) {
        try {
            CharacterList character = characterRepository.findByUserIdAndBasicInfo_CharacterName(userId, characterName);
            if (character == null) {
                log.warn("Character not found for user {} with name {}", userId, characterName);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(character);
        } catch (Exception e) {
            log.error("Error retrieving character data: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving character data: " + e.getMessage());
        }
    }


    private CharacterListResponseDto convertToDto(CharacterList character) {
        try {
            CharacterListResponseDto dto = new CharacterListResponseDto();
            dto.setId(character.getId());

            if (character.getBasicInfo() != null) {
                dto.setName(character.getBasicInfo().getCharacterName());
                dto.setLevel(character.getBasicInfo().getCharacterLevel());
                dto.setRace(character.getBasicInfo().getCharacterRace());
                dto.setAlignment(character.getBasicInfo().getAlignment());
                dto.setExperience(character.getBasicInfo().getExperience());
                dto.setBackground(character.getBasicInfo().getBackground());
            }

            return dto;
        } catch (Exception e) {
            log.error("Error converting character to DTO: {}", e.getMessage());
            throw new RuntimeException("Error converting character to DTO", e);
        }
    }

    @Override
    public ResponseEntity<?> updateAbilities(String characterId, Map<String, Object> abilities) {
        return updateCharacterField(characterId, "abilities", abilities);
    }

    @Override
    public ResponseEntity<?> updateVitalStats(String characterId, Map<String, Object> vitalStats) {
        return updateCharacterField(characterId, "vitalStats", vitalStats);
    }

    @Override
    public ResponseEntity<?> updateField(String characterId, String fieldName, Map<String, Object> fieldData) {
        try {
            Query query = new Query(Criteria.where("id").is(characterId));
            Update update = new Update();

            fieldData.forEach((key, value) -> {
                String fullPath = fieldName + "." + key;
                update.set(fullPath, value);
            });

            update.set("lastUpdated", LocalDateTime.parse("2025-06-14 12:38:42",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            UpdateResult result = mongoTemplate.updateFirst(query, update, CharacterList.class);

            if (result.getModifiedCount() > 0) {
                log.info("Successfully updated field {} for character {}", fieldName, characterId);
                return ResponseEntity.ok().build();
            } else {
                log.warn("Character {} not found or no changes made", characterId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error updating field {} for character {}: {}", fieldName, characterId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> updateBasicInfo(String characterId, Map<String, Object> basicInfo) {
        try {
            Query query = new Query(Criteria.where("id").is(characterId));
            Update update = new Update();

            // Подготовка валидированных данных
            Map<String, Object> validatedBasicInfo = new HashMap<>();

            // Обработка каждого поля с проверкой типа
            validatedBasicInfo.put("characterName", basicInfo.get("characterName").toString());
            validatedBasicInfo.put("characterClass", basicInfo.get("characterClass").toString());
            validatedBasicInfo.put("characterRace", basicInfo.get("characterRace").toString());
            validatedBasicInfo.put("characterLevel", ((Number) basicInfo.get("characterLevel")).intValue());

            // Обработка опциональных полей
            if (basicInfo.containsKey("experience")) {
                validatedBasicInfo.put("experience", ((Number) basicInfo.get("experience")).intValue());
            }
            if (basicInfo.containsKey("background")) {
                validatedBasicInfo.put("background", basicInfo.get("background").toString());
            }
            if (basicInfo.containsKey("alignment")) {
                validatedBasicInfo.put("alignment", basicInfo.get("alignment").toString());
            }

            // Полное обновление объекта basicInfo
            update.set("basicInfo", validatedBasicInfo);
            update.set("lastUpdated", LocalDateTime.now());

            UpdateResult result = mongoTemplate.updateFirst(query, update, CharacterList.class);

            if (result.getModifiedCount() == 0) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().build();
        } catch (ClassCastException e) {
            log.error("Type conversion error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid data types in request"));
        } catch (Exception e) {
            log.error("Update error: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to update character"));
        }
    }

    @Override
    public ResponseEntity<?> updateSkills(String characterId, Map<String, Object> skillsData) {
        try {
            Query query = new Query(Criteria.where("id").is(characterId));
            Update update = new Update();

            log.debug("Updating skills for character {}: {}", characterId, skillsData);

            // Обработка спасбросков
            if (skillsData.containsKey("savingThrows")) {
                update.set("skills.savingThrows", skillsData.get("savingThrows"));
            }

            // Обработка обновления proficiencyBonus
            if (skillsData.containsKey("proficiencyBonus")) {
                update.set("skills.proficiencyBonus", skillsData.get("proficiencyBonus"));
            }

            // Обработка обновления языков
            if (skillsData.containsKey("languages")) {
                update.set("skills.languages", skillsData.get("languages"));
            }

            // Обработка обновления навыков
            if (skillsData.containsKey("skills")) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> skills = (List<Map<String, Object>>) skillsData.get("skills");

                if (!skills.isEmpty()) {
                    update.set("skills.skills", skills);
                }
            }

            // Обновляем дату последнего изменения
            update.set("lastUpdated", LocalDateTime.now());

            log.debug("Final update object: {}", update);

            UpdateResult result = mongoTemplate.updateFirst(query, update, CharacterList.class);

            if (result.getModifiedCount() > 0) {
                log.info("Successfully updated skills for character {}", characterId);
                return ResponseEntity.ok().build();
            } else {
                // Добавляем дополнительную проверку
                CharacterList character = mongoTemplate.findOne(query, CharacterList.class);
                if (character == null) {
                    log.warn("Character {} not found", characterId);
                    return ResponseEntity.notFound().build();
                } else {
                    log.info("No changes made for character {}", characterId);
                    return ResponseEntity.ok().build();
                }
            }
        } catch (Exception e) {
            log.error("Error updating skills for character {}: {}", characterId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> updatePersonality(String characterId, Map<String, Object> personality) {
        return updateCharacterField(characterId, "personality", personality);
    }

    @Override
    public ResponseEntity<?> updateSpells(String characterId, Map<String, Object> spellsData) {
        try {
            Query query = new Query(Criteria.where("id").is(characterId));
            Update update = new Update();

            // Handle spell modifiers
            if (spellsData.containsKey("spellModifiers")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> modifiers = (Map<String, Object>) spellsData.get("spellModifiers");
                SpellModifiers spellModifiers = new SpellModifiers();
                spellModifiers.setSpellAbilityScore((String) modifiers.get("spellAbilityScore"));
                spellModifiers.setSpellModifier(((Number) modifiers.get("spellModifier")).longValue());
                spellModifiers.setSpellSaveThrow(((Number) modifiers.get("spellSaveThrow")).longValue());
                update.set("spells.spellModifiers", spellModifiers);
            }

            // Handle spells list
            if (spellsData.containsKey("spell")) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> spellsList = (List<Map<String, Object>>) spellsData.get("spell");
                update.set("spells.spell", spellsList);
            }

            update.set("lastUpdated", LocalDateTime.now());

            UpdateResult result = mongoTemplate.updateFirst(query, update, CharacterList.class);

            if (result.getModifiedCount() > 0) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating spells: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateSubInfo(String characterId, Map<String, Object> subInfo) {
        try {
            Query query = new Query(Criteria.where("id").is(characterId));
            Update update = new Update();

            // Обработка proficiencies отдельно, если они присутствуют
            if (subInfo.containsKey("proficiencies")) {
                @SuppressWarnings("unchecked")
                Map<String, String> proficiencies = (Map<String, String>) subInfo.get("proficiencies");
                update.set("subInfo.proficiencies", proficiencies);
            }

            // Остальные поля
            if (subInfo.containsKey("species")) {
                update.set("subInfo.species", subInfo.get("species"));
            }
            if (subInfo.containsKey("equipment")) {
                update.set("subInfo.equipment", subInfo.get("equipment"));
            }
            if (subInfo.containsKey("currencies")) {
                update.set("subInfo.currencies", subInfo.get("currencies"));
            }

            update.set("lastUpdated", LocalDateTime.now());

            UpdateResult result = mongoTemplate.updateFirst(query, update, CharacterList.class);

            if (result.getModifiedCount() > 0) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating character: " + e.getMessage());
        }
    }

    private ResponseEntity<?> updateCharacterField(String characterId, String fieldPath, Map<String, Object> fieldData) {
        try {
            Query query = new Query(Criteria.where("id").is(characterId));
            Update update = new Update();

            fieldData.forEach((key, value) -> {
                String fullPath = fieldPath + "." + key;
                update.set(fullPath, value);
            });

            // Обновляем lastUpdated
            update.set("lastUpdated", LocalDateTime.parse("2025-06-14 11:55:16",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            UpdateResult result = mongoTemplate.updateFirst(query, update, CharacterList.class);

            if (result.getModifiedCount() > 0) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating character: " + e.getMessage());
        }
    }
}
