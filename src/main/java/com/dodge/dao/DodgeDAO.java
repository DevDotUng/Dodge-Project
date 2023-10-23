package com.dodge.dao;

import com.dodge.dto.RoomDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DodgeDAO {
    void addUser(String sessionId);
    List<String> getHomeUsers();
    void deleteUser(String sessionId);
    List<RoomDTO> getRooms();
    void createRoom(String title, String host);
    void updateHostId(String title, String host);
    void updateGuestId(String title, String guest);
    String getTitle(String host);
    void deleteRoom(String host);
}
