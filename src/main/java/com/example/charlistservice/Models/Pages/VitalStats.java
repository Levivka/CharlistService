package com.example.charlistservice.Models.Pages;

import com.example.charlistservice.Models.HitDice;
import lombok.Data;

@Data
public class VitalStats {
    private int armorClass;
    private int inspiration;
    private int speed;
    private int currentHitPoints;
    private int temporaryHitPoints;
    private HitDice hitDice;
    private DeathSaves deathSaves;

    @Data
    public static class DeathSaves {
        private int successes;
        private int failures;
    }
}
