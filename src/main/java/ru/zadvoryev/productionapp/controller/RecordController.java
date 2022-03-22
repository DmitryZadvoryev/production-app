package ru.zadvoryev.productionapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zadvoryev.productionapp.data.Role;
import ru.zadvoryev.productionapp.data.User;
import ru.zadvoryev.productionapp.dto.LineDto;
import ru.zadvoryev.productionapp.dto.RecordDto;
import ru.zadvoryev.productionapp.service.LineService;
import ru.zadvoryev.productionapp.service.RecordService;

import javax.validation.Valid;
import java.time.LocalDate;


@Controller
@RequestMapping("/lines/line/")
public class RecordController {

    final RecordService recordService;

    final LineService lineService;

    public RecordController(RecordService recordService, LineService lineService) {
        this.recordService = recordService;
        this.lineService = lineService;
    }

    /**
     * Если все параметры null, то возвращается полный список list для линии с id, иначе возвращаются записи
     * отфильрованные методом filter например, по фамилии исполнителя
     *
     * @param id                 - ид линии
     * @param start              - начальная дата
     * @param end                - конечная дата
     * @param nameOfOrganization - название организации
     * @param nameOfProduct      - наименование изделия
     * @param variant            - вариант исполнения
     * @param side               - сторона изделия
     * @param surname            - фамилия исполнителя
     * @param pageable           - пагинация
     * @return line/lines - страница линии со списком изделий,
     */
    @GetMapping("{lineId}")
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
                       @PageableDefault(size = 15) Pageable pageable,
                       Model model) {

        LineDto line = lineService.getOne(id);
        Page<RecordDto> page = null;

        if ((start == null || end == null) &&
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

        model.addAttribute("page", page);
        model.addAttribute("line", line);
        return "records/records";
    }

    /**
     * Форма для добавления новой записи
     *
     * @param model
     * @param lineId - ид линии
     * @return возвращает форму для добавления новой записи
     */
    @GetMapping("{lineId}/record-create")
    public String showCreateForm(Model model, @PathVariable("lineId") String lineId) {
        model.addAttribute("record", new RecordDto());
        return "records/record-create";
    }

    /**
     * Добавление новой записи
     *
     * @param user          - авторизированный пользователь
     * @param id            - ид линии
     * @param record        - класс представлябщий запись
     * @param bindingResult - валидация
     * @return перенаправляет страницу с записями для линии с id
     */
    @PostMapping("{lineId}/record-create")
    public String create(@AuthenticationPrincipal User user,
                         @PathVariable("lineId") long id,
                         @Valid @ModelAttribute("record") RecordDto record,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "records/record-create";
        }

        recordService.create(record, user, id);

        return "redirect:/lines/line/" + id;
    }

    /**
     * Форма обновлени записи
     *
     * @param lineId - ид линии к которой относятся записи
     * @param id     -  ид записи редактироуемой
     * @return страница для обновления
     */

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERIOR')")
    @GetMapping("{lineId}/record-update/{id}")
    public String updateRecordForm(@PathVariable("lineId") String lineId, @PathVariable("id") Long id, Model model) {
        RecordDto record = recordService.getOne(id);
        model.addAttribute("record", record);
        model.addAttribute("lineid", lineId);

        return "records/record-update";
    }

    /**
     * Обновление записи
     *
     * @param lineId        - ид линии к которой относятся записи
     * @param record        - класс запись
     * @param bindingResult - валидация
     * @return перенаправляет страницу с записями для линии с id
     */

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERIOR')")
    @PostMapping("{lineId}/record-update/{id}")
    public String updateRecord(@PathVariable("lineId") String lineId,
                               @Valid @ModelAttribute RecordDto record,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "records/record-update";
        }
        recordService.update(record);
        model.addAttribute("record", record);
        return "redirect:/lines/line/" + lineId;
    }

    /**
     * Удаление записи
     *
     * @param id - ид записи
     * @return перенаправляет страницу с записями для линии с id
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERIOR')")
    @GetMapping("{lineId}/record-delete/{id}")
    public String delete(@PathVariable("id") long id) {
        recordService.delete(id);
        return "redirect:/lines/line/{lineId}";
    }

    @ModelAttribute("admin")
    public Role getAdmin() {
        return Role.ADMIN;
    }
}
