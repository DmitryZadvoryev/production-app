package ru.zadvoryev.productionapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.zadvoryev.productionapp.data.Line;
import ru.zadvoryev.productionapp.data.Role;
import ru.zadvoryev.productionapp.dto.LineDto;
import ru.zadvoryev.productionapp.service.LineService;

import javax.validation.Valid;
import java.util.List;


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


    @GetMapping
    public String list(Model model) {
        List<LineDto> lines = lineService.list();
        model.addAttribute("lines", lines);
        return "main";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/line-create")
    public String showCreateForm(Line line, Model model) {
        model.addAttribute("line", line);
        return "line-create";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/line-create")
    public String create(@Valid @ModelAttribute("line") LineDto line,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "line-create";
        }
        lineService.createOrUpdate(line);
        return "redirect:/";
    }


    @GetMapping("/line-update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        LineDto line = lineService.getOne(id);
        model.addAttribute("line", line);
        return "/line-update";
    }


    @PostMapping("/line-update")
    public String update(@Valid @ModelAttribute("line") LineDto line,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/line-update";
        }
        lineService.createOrUpdate(line);
        return "redirect:/";
    }


    @GetMapping("/line-delete/{id}")
    public String delete(@PathVariable("id") long id) {
        lineService.delete(id);
        return "redirect:/";
    }
}
