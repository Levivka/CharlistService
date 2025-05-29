package com.example.charlists.Repository;

import com.example.charlists.Model.Character.Charlists;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharlistRepository extends MongoRepository<Charlists, String> {
//    List<Charlists> getAllBy
}
