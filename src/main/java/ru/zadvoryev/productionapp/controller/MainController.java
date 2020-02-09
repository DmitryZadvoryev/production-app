package ru.zadvoryev.productionapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zadvoryev.productionapp.data.Role;
import ru.zadvoryev.productionapp.dto.LineDto;
import ru.zadvoryev.productionapp.service.LineService;

import java.util.List;

/**
 * Cписок линий, добавление, удаление, редактирование
 */
@Controller
@RequestMapping("/")
public class MainController {


    final LineService lineService;

    public MainController(LineService lineService) {
        this.lineService = lineService;
    }

    @ModelAttribute("admin")
    public Role getAdmin() {
        return Role.ADMIN;
    }

    @ModelAttribute("superior")
    public Role getSuperior() {
        return Role.SUPERIOR;
    }

    /*
        Список всех линий
    */
    @GetMapping
    public String list(Model model) {
        List<LineDto> lines = lineService.list();
        model.addAttribute("lines", lines);
        return "main";
    }
}
