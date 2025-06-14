package com.example.charlistservice.Models.DTO;

import lombok.Data;

@Data
public class CharacterListResponseDto {
    private String id;
    private String name;
    private int level;
    private String race;
    private String alignment;
    private Integer experience;
    private String background;
}
