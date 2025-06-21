package com.example.charlistservice.Models.Pages;

import com.example.charlistservice.Models.SpellModifiers;
import com.example.charlistservice.Models.Spells;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class CharacterSpells {
    private SpellModifiers spellModifiers = new SpellModifiers();
    private List<Spells> spell;

    public CharacterSpells() {
        this.spellModifiers = new SpellModifiers();
        this.spell = new ArrayList<>();
    }
}
