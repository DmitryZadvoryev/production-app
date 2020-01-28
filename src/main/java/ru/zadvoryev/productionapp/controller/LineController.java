package ru.zadvoryev.productionapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zadvoryev.productionapp.data.Line;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.data.Role;
import ru.zadvoryev.productionapp.data.User;
import ru.zadvoryev.productionapp.dto.RecordDto;
import ru.zadvoryev.productionapp.repository.LineRepository;
import ru.zadvoryev.productionapp.repository.RecordRepository;
import ru.zadvoryev.productionapp.service.RecordService;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/lines/line/{lineId}")
public class LineController {


    @Autowired
    RecordRepository recordRepository;

    @Autowired
    LineRepository lineRepository;

    @Autowired
    RecordService recordService;


    @GetMapping
    public String list(@PathVariable("lineId") long id,
                       @RequestParam(name = "start", required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                       @RequestParam(name = "end", required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                       @RequestParam(name = "nameOfOrganization", required = false) String nameOfOrganization,
                       @RequestParam(name = "nameOfProduct", required = false) String nameOfProduct,
                       @RequestParam(name = "variant", required = false) String variant,
                       @RequestParam(name = "side", required = false) String side,
                       @RequestParam(name = "surname", required = false) String surname,
                       @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {

        Page<RecordDto> page = null;

        if (start == null || end == null &&
                (nameOfOrganization == null || nameOfOrganization.isEmpty()) &&
                (nameOfProduct == null || nameOfProduct.isEmpty()) &&
                (variant == null || variant.isEmpty()) &&
                (side == null || side.isEmpty()) &&
                (surname == null || surname.isEmpty())) {

            page = recordService.list(id, pageable);

        } else {
            if (start == null && end == null) {
                start = LocalDate.of(0000, 01, 01);
                end = LocalDate.of(999999, 01, 01);
            }
            page = recordService.filter(id, start, end, nameOfOrganization, nameOfProduct, variant,
                    side, surname, pageable);
        }
        Line line = lineRepository.getOne(id);
        model.addAttribute("page", page);
        model.addAttribute("line", line);
        return "lines/line";
    }

    @GetMapping("record-create")
    public String showCreateForm(@PathVariable("lineId") long id, Record record, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("record", record);
        return "lines/record-create";
    }

    @PostMapping("record-create")
    public String create(@AuthenticationPrincipal User user,
                         @PathVariable("lineId") long id,
                         @Valid @ModelAttribute("record") Record record,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lines/record-create";
        }
        record.setAuthor(user);
        Line line = lineRepository.getOne(id);
        record.setLine(line);
        recordRepository.save(record);
        //recordService.create(record,user,id);

        return "redirect:/lines/line/" + record.getLine().getId();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("record-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        Record record = recordRepository.getRecordById(id);
        //RecordDto record = recordService.getOne(id);
        model.addAttribute("record", record);
        return "lines/record-update";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("record-update")
    public String updateUser(@Valid @ModelAttribute("record") Record record, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lines/record-update";
        }
        recordRepository.save(record);
        //recordService.udpate(record);
        return "redirect:/lines/line/{lineId}";
    }

    @GetMapping("record-delete/{id}")
    public String delete(@PathVariable("id") long id) {
        recordRepository.deleteById(id);
        return "redirect:/lines/line/{lineId}";
    }

    @ModelAttribute("admin")
    public Role getAdmin() {
        return Role.ADMIN;
    }
}
