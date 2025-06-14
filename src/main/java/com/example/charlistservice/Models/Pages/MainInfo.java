package com.example.charlistservice.Models.Pages;

import lombok.Data;

@Data
public class MainInfo {
    private String characterName;
    private String characterClass;
    private String characterRace;
    private short characterLevel;
    private int experience;
    private String background;
    private String alignment;
}
