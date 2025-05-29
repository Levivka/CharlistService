package com.example.charlists.Services.Impl;

import com.example.charlists.Model.Character.Dto.CharlistMainInfo;
import com.example.charlists.Services.CharlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharlistServiceImpl implements CharlistService {
    @Override
    public ResponseEntity<?> findAll() {
        return ;
    }
}
