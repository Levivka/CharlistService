package com.example.charlistservice.Models.Pages;

import com.example.charlistservice.Models.Equipment;
import com.example.charlistservice.Models.Species;
import lombok.Data;

import java.util.List;

@Data
public class PersonalityTraits {
    private String personality;
    private String ideals;
    private String bonds;
    private String flaws;
    private String appearance;
    private String backstory;
    private List<String> allies;
    private List<String> enemies;
    private String birthplace;
    private int age;
    private String eyeColor;
    private String hairColor;
    private String height;
    private String weight;
    private String skin;
}
