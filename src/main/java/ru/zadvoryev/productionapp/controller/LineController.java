package ru.zadvoryev.productionapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zadvoryev.productionapp.data.Line;
import ru.zadvoryev.productionapp.dto.LineDto;
import ru.zadvoryev.productionapp.service.LineService;

import javax.validation.Valid;
import java.util.List;

    @Controller
    @RequestMapping("/lines")
    public class LineController {


        final LineService lineService;

        public LineController(LineService lineService) {
            this.lineService = lineService;
        }

        /*
            Список всех линий
        */
        @GetMapping
        public String list(Model model) {
            List<LineDto> lines = lineService.list();
            model.addAttribute("lines", lines);
            return "lines/lines";
        }

        /*
            Переход к форме для созания новой линии
        */
        @PreAuthorize("hasAuthority('ADMIN')")
        @GetMapping("/line-create")
        public String showCreateForm(Line line, Model model) {
            model.addAttribute("line", line);
            return "lines/line-create";
        }

        /*
            Создание новой линии
        */
        @PreAuthorize("hasAuthority('ADMIN')")
        @PostMapping("/line-create")
        public String create(@Valid @ModelAttribute("line") LineDto line,
                             BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "lines/line-create";
            }
            lineService.createOrUpdate(line);
            return "redirect:/lines";
        }

        /*
            Форма для редактирования линии
        */
        @PreAuthorize("hasAuthority('ADMIN')")
        @GetMapping("/line-update/{id}")
        public String showUpdateForm(@PathVariable("id") Long id, Model model) {
            LineDto line = lineService.getOne(id);
            model.addAttribute("line", line);
            return "/lines/line-update";
        }

        /*
            Обновление линии
        */
        @PreAuthorize("hasAuthority('ADMIN')")
        @PostMapping("/line-update")
        public String update(@Valid @ModelAttribute("line") LineDto line,
                             BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "lines/line-update";
            }
            lineService.createOrUpdate(line);
            return "redirect:/lines";
        }

        /*
            Удаление линии
        */
        @PreAuthorize("hasAuthority('ADMIN')")
        @GetMapping("/line-delete/{id}")
        public String delete(@PathVariable("id") long id) {
            lineService.delete(id);
            return "redirect:/lines";
        }
}
