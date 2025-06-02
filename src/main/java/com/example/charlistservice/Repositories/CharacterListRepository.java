package com.example.charlistservice.Repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterListRepository extends MongoRepository<Character, String> {

}
