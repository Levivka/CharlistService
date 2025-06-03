package com.example.charlistservice.Repositories;


import com.example.charlistservice.Models.CharacterList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterListRepository extends MongoRepository<CharacterList, String> {
    List<CharacterList> findByUserId(String userId);
}
