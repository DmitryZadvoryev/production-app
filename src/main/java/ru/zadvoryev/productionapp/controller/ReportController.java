package ru.zadvoryev.productionapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.dto.DistinctProductDto;
import ru.zadvoryev.productionapp.dto.LineDto;
import ru.zadvoryev.productionapp.dto.ProductivityDto;
import ru.zadvoryev.productionapp.dto.ReportForTimeDto;
import ru.zadvoryev.productionapp.repository.LineRepository;
import ru.zadvoryev.productionapp.repository.RecordRepository;
import ru.zadvoryev.productionapp.converter.RecordConverter;
import ru.zadvoryev.productionapp.service.LineService;
import ru.zadvoryev.productionapp.service.RecordService;
import ru.zadvoryev.productionapp.util.ReportUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
public class ReportForTimeController {


    @ModelAttribute("distinct")
    public List<DistinctProductDto> getDistinctRecordsList() {
        return recordService.getDistinctRecordsList();
    }

    @ModelAttribute("lines")
    public List<LineDto> getLinesList() {
        return lineService.list();
    }

    @Autowired
    RecordService recordService;

    @Autowired
    LineService lineService;


    @GetMapping("/report-for-time")
    public String report(@RequestParam(name = "start", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                         @RequestParam(name = "end", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                         Model model) {

      /*  List<Long> allId = lineRepository.findAllId();
        if (start != null && end != null) {
            List<List<ReportForTimeDto>> results = ReportUtil.getReportForTime(start, end, allId, recordRepository);
            model.addAttribute("list", results);
       }*/

        List<ReportForTimeDto> recordsForReport = recordService.getRecordsForReport(start, end);

        Map<List<String>, List<ReportForTimeDto>> collect = recordsForReport.stream()
                .collect(Collectors.groupingBy(record -> Arrays.asList(record.getLineName(),
                                                                       record.getManePr(),
                                                                       record.getVar(),
                                                                       record.getSide())));

        model.addAttribute("list", collect);

        return "amount-per-time";
    }

    @GetMapping("/productivity")
    public String report(@RequestParam(name = "start", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                         @RequestParam(name = "end", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                         @RequestParam(name = "line", required = false) String line,
                         @RequestParam(name = "product", required = false) String product,
                         Model model, HttpServletResponse res) throws IOException {
        if (start != null || end != null) {
            String[] split = product.split(",");
            List<ProductivityDto> records = recordService.getRecordsForProductivityReport(start, end, Long.valueOf(line),
                    split[0], split[1], split[2]);

            float periodAvg = ReportUtil.getPeriodAvg(records);

            model.addAttribute("periodAvg", periodAvg);
            model.addAttribute("records", records);
        }

        return "productivity-report";
    }

}
