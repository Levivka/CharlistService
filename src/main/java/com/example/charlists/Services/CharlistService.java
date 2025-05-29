package com.example.charlists.Services;

import com.example.charlists.Model.Character.Dto.CharlistMainInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CharlistService {
    ResponseEntity<?> findAll();
}
