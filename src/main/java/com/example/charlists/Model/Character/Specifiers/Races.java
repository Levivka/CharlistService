package com.example.charlists.Model.Character.Specifiers;

import com.example.charlists.Model.Character.Description.Language;
import com.example.charlists.Model.Character.Modifiers.ModifiersBonus;

import java.util.List;

public class Races {
    public String name;
    public short speed;
    public List<ModifiersBonus> statBonuses;
    public String age;
    public String size;
    public String sizeDescription;
    public List<Proficiencies> startingProficiencies;
    public List<Language> languages;
    public List<Traits> traits;
    public List<Races> subRaces;



}
