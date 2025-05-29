package com.example.charlists.Services.Impl;

import com.example.charlists.Model.Character.Equipment.Category.Weapon;
import com.example.charlists.Model.Character.Equipment.Equipment;
import com.example.charlists.Model.Character.Equipment.EquipmentCategory;
import com.example.charlists.Repository.EquipmentRepository;
import com.example.charlists.Services.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;

    @Override
    public ResponseEntity<?> getWeapons() {
        return ResponseEntity.ok(equipmentRepository.findAllByEquipmentCategory_Name("Weapon"));
    }
}