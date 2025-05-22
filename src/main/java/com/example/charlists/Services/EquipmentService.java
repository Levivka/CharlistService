package com.example.charlists.Services;

import com.example.charlists.Model.Character.Equipment.Category.Weapon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentService {
    List<Weapon> getWeapons(Weapon weapon);
}
