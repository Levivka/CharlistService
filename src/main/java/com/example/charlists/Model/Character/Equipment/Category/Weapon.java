package com.example.charlists.Model.Character.Equipment.Category;

import com.example.charlists.Model.Character.Equipment.Equipment;

import java.util.List;

public class Weapon extends Equipment {
    private String weaponCategory;
    private String weaponRange;
    private String categoryRange;
    private Damage damage;
    private short range;
    private short weight;
    private List<String> properties;
}
