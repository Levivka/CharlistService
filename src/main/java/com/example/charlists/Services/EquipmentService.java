package com.example.charlists.Services;

import com.example.charlists.Model.Character.Equipment.Category.Weapon;
import com.example.charlists.Model.Character.Equipment.Equipment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentService {
    ResponseEntity<?> getWeapons();
}
