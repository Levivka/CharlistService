package com.example.charlistservice.Models;

import lombok.Data;

@Data
public class Abilities {
    private AbilityScore strength;
    private AbilityScore dexterity;
    private AbilityScore constitution;
    private AbilityScore intelligence;
    private AbilityScore wisdom;
    private AbilityScore charisma;

    @Data
    public static class AbilityScore {
        private int value;
        private int modifier;
    }
}
