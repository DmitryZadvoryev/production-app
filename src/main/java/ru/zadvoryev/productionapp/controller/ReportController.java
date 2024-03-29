package ru.zadvoryev.productionapp.controller;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.zadvoryev.productionapp.dto.ReportDto;
import ru.zadvoryev.productionapp.service.RecordService;
import ru.zadvoryev.productionapp.util.ExcelReportForTime;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static ru.zadvoryev.productionapp.util.ReportUtil.createReport;


@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERIOR')")
@RequestMapping("/reports")
public class ReportController {

    final RecordService recordService;

    public ReportController(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * Отчет за период от start до end
     *
     * @param start - начальная дата
     * @param end   - конечная дата
     * @return записи от начальной даты до конечной
     */

    @GetMapping("/report-for-time")
    public String report(@RequestParam(name = "start", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                         @RequestParam(name = "end", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                         Model model) {

        if (start != null && end != null) {
            List<ReportDto> recordsForReport = recordService.getRecords(start, end);
            List<List<ReportDto>> report = createReport(recordsForReport);
            model.addAttribute("list", report);
        }

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "reports/amount-per-time-report";
    }

    /**
     * Скачивание файла report-all-lines.xlsx
     */
    @GetMapping("/report-for-time/download-excel")
    public void downloadExcel(@RequestParam(name = "start", required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                              @RequestParam(name = "end", required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                              HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = all_lines_report.xlsx");
        List<ReportDto> records = recordService.getRecords(start, end);
        List<List<ReportDto>> report = createReport(records);
        ByteArrayInputStream stream = ExcelReportForTime.toFile(report, start, end);
        IOUtils.copy(stream, response.getOutputStream());
    }
}
