package com.example.charlistservice.Models.Pages;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SkillsProficiencies {
    private List<Skill> skills;
    private int proficiencyBonus;
    private List<String> languages;
    private Map<String, Boolean> savingThrows;


    @Data
    public static class Skill {
        private String name;
        private boolean proficient;
    }
}