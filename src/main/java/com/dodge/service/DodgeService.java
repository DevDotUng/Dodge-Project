package com.dodge.service;

import com.dodge.dto.RoomDTO;

import java.util.List;

public interface DodgeService {
    void addUser(String sessionId);
    List<String> getHomeUsers();
    void deleteUser(String sessionId);
    List<RoomDTO> getRooms();
    void createRoom(String title, String host);
    void updateHostId(String title, String host);
    void updateGuestId(String title, String guest);
    String deleteRoom(String host);
}
