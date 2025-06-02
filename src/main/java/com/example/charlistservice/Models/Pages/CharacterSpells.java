package com.example.charlistservice.Models.Pages;

import com.example.charlistservice.Models.SpellModifiers;
import com.example.charlistservice.Models.Spells;
import lombok.Data;

import java.util.List;

@Data
public class CharacterSpells {
    public SpellModifiers spellModifiers;
    public List<Spells> spell;
}
