package com.dodge.service;

import com.dodge.dao.DodgeDAO;
import com.dodge.dto.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DodgeServiceImp implements DodgeService {

    @Autowired
    DodgeDAO dodgeDAO;

    @Override
    public void addUser(String sessionId) {
        dodgeDAO.addUser(sessionId);
    }

    @Override
    public List<String> getHomeUsers() {
        return dodgeDAO.getHomeUsers();
    }

    @Override
    public void deleteUser(String sessionId) {
        dodgeDAO.deleteUser(sessionId);
    }

    @Override
    public List<RoomDTO> getRooms() {
        return dodgeDAO.getRooms();
    }

    @Override
    public void createRoom(String title, String host) {
        dodgeDAO.createRoom(title, host);
    }

    @Override
    public void updateHostId(String title, String host) {
        dodgeDAO.updateHostId(title, host);
    }

    @Override
    public void updateGuestId(String title, String guest) {
        dodgeDAO.updateGuestId(title, guest);
    }

    @Override
    public String deleteRoom(String host) {
        String title = dodgeDAO.getTitle(host);
        dodgeDAO.deleteRoom(host);

        return title;
    }

    @Override
    public String getGuestId(String host) {
        return dodgeDAO.getGuestId(host);
    }
}
