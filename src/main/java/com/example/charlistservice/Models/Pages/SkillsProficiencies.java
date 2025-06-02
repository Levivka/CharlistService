package com.example.charlistservice.Models.Pages;

import lombok.Data;

import java.util.List;

@Data
public class SkillsProficiencies {
    private List<Skill> skills;
    private int proficiencyBonus;

    @Data
    public static class Skill {
        private String name;
        private boolean proficient;
    }
}