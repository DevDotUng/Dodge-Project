package com.dodge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RoomDTO {
    private int id;
    private String title;
    private String host;
    private String guest;
}
