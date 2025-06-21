package com.example.charlistservice.Repositories;


import com.example.charlistservice.Models.CharacterList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


import java.util.List;

@Repository
public interface CharacterListRepository extends MongoRepository<CharacterList, String> {
    List<CharacterList> findByUserId(String userId);
    CharacterList findByUserIdAndBasicInfo_CharacterName(String userId, String characterName);
}
