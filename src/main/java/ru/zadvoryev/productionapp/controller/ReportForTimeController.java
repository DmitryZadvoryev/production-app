package ru.zadvoryev.productionapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.dto.ReportForTimeDto;
import ru.zadvoryev.productionapp.repository.LineRepository;
import ru.zadvoryev.productionapp.repository.RecordRepository;
import ru.zadvoryev.productionapp.util.RecordConverter;
import ru.zadvoryev.productionapp.util.ReportUtil;

import static ru.zadvoryev.productionapp.util.ReportUtil.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportForTimeController {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    LineRepository lineRepository;

    @Autowired
    RecordConverter converter;


    @GetMapping("/report-for-time")
    public String report(@RequestParam(name = "start", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                         @RequestParam(name = "end", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                         Model model) {

        List<Long> allId = lineRepository.findAllId();
        if (start != null && end != null) {
            List<List<ReportForTimeDto>> results = ReportUtil.getReportForTime(start, end, allId, recordRepository);
            model.addAttribute("list", results);
        }
        return "amount-per-time";
    }

}
