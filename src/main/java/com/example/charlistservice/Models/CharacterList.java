package com.example.charlistservice.Models;

import com.example.charlistservice.Models.Pages.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "characters")
@Data
public class CharacterList {
    @Id
    private String id;
    private String userId;

    private MainInfo basicInfo;

    private VitalStats vitalStats;

    private Abilities abilities;
    private SkillsProficiencies skills;

    private PersonalityTraits personality;

    private CharacterSpells spells;

    private SubInfo subInfo;

    private Date createdAt;
    private Date lastUpdated;
}
