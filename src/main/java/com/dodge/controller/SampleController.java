package com.dodge.controller;

import com.dodge.service.DodgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class SampleController {

    @Autowired
    DodgeService dodgeService;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("rooms", dodgeService.getRooms());
        return "home";
    }

    @GetMapping("game/{title}/{type}")
    public String game(Model model, @PathVariable("title") String title, @PathVariable("type") String type) {
        return "game";
    }
}
