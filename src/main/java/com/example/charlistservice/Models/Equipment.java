package com.example.charlistservice.Models;

import lombok.Data;

@Data
public class Equipment {
    private String name;
    private String stat;
    private String statType;
    private double weight;
    private int quantity;
    private String description;
    private int cost;
    private String rarity;
    private boolean equipped;
    private String category;
}
