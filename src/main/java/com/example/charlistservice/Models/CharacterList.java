package com.example.charlistservice.Models;

import com.example.charlistservice.Models.Pages.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "characters")
@Data
public class CharacterList {
    private String id;
    private String userId;

    private MainInfo basicInfo;

    private VitalStats vitalStats;

    private Abilities abilities;
    private SkillsProficiencies skills;

    private PersonalityTraits personality;

    private List<CharacterSpells> spells;

    private SubInfo subInfo;
}
