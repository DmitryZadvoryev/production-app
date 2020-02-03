package ru.zadvoryev.productionapp.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import ru.zadvoryev.productionapp.dto.DistinctProductDto;
import ru.zadvoryev.productionapp.dto.LineDto;
import ru.zadvoryev.productionapp.dto.ProductivityDto;
import ru.zadvoryev.productionapp.dto.ReportForTimeDto;
import ru.zadvoryev.productionapp.service.LineService;
import ru.zadvoryev.productionapp.service.RecordService;
import ru.zadvoryev.productionapp.util.ReportUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.zadvoryev.productionapp.util.ReportUtil.getReport;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERIOR')")
@RequestMapping("/reports")
public class ReportController {

    final RecordService recordService;

    final LineService lineService;

    public ReportController(RecordService recordService, LineService lineService) {
        this.recordService = recordService;
        this.lineService = lineService;
    }


    @ModelAttribute("distinct")
    public List<DistinctProductDto> getDistinctRecordsList() {
        return recordService.getDistinctRecordsList();
    }

    @ModelAttribute("lines")
    public List<LineDto> getLinesList() {
        return lineService.list();
    }


    @GetMapping("/report-for-time")
    public String report(@RequestParam(name = "start", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                         @RequestParam(name = "end", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                         Model model) {

        if (start != null && end != null) {
            List<ReportForTimeDto> recordsForReport = recordService.getRecordsForReport(start, end);
            List<List<ReportForTimeDto>> report = getReport(recordsForReport);
            model.addAttribute("list", report);
        }

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "amount-per-time";
    }

    @GetMapping("/productivity")
    public String report(@RequestParam(name = "start", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                         @RequestParam(name = "end", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                         @RequestParam(name = "line", required = false) String line,
                         @RequestParam(name = "product", required = false) String product,
                         Model model) {

        if ((start != null || end != null) && !StringUtils.isEmpty(product) && !StringUtils.isEmpty(line)) {
            String[] split = product.split(",");
            List<ProductivityDto> records = recordService.getRecordsForProductivityReport(start, end, Long.valueOf(line),
                    split[0], split[1], split[2]);

            float periodAvg = ReportUtil.getPeriodAvg(records);

            model.addAttribute("periodAvg", periodAvg);
            model.addAttribute("records", records);

        } else if ((start == null || end == null) && !StringUtils.isEmpty(product) && !StringUtils.isEmpty(line)) {
            String[] split = product.split(",");
            start = LocalDate.of(0000, 01, 01);
            end = LocalDate.of(999999, 01, 01);
            List<ProductivityDto> records = recordService.getRecordsForProductivityReport(start, end, Long.valueOf(line),
                    split[0], split[1], split[2]);

            float periodAvg = ReportUtil.getPeriodAvg(records);

            model.addAttribute("periodAvg", periodAvg);
            model.addAttribute("records", records);

        }
        return "productivity-report";
    }
}
