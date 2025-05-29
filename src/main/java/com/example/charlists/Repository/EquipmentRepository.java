package com.example.charlists.Repository;

import com.example.charlists.Model.Character.Equipment.Equipment;
import com.example.charlists.Model.Character.Equipment.EquipmentCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends MongoRepository<Equipment, String> {
    List<Equipment> findAllByEquipmentCategory_Name(String name);
}
