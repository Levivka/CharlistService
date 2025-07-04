package com.example.charlistservice.Models.Pages;

import com.example.charlistservice.Models.Currencies;
import com.example.charlistservice.Models.Equipment;
import com.example.charlistservice.Models.Species;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SubInfo {
    private List<Species> species;
    private List<Equipment> equipment;
    private List<Currencies> currencies;
    private Map<String, String> savingThrows;
}
