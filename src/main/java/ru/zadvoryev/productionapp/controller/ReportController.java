package ru.zadvoryev.productionapp.controller;

import org.apache.commons.compress.utils.IOUtils;
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
import ru.zadvoryev.productionapp.report.ExcelReportForTimeReport;
import ru.zadvoryev.productionapp.service.LineService;
import ru.zadvoryev.productionapp.service.RecordService;
import ru.zadvoryev.productionapp.util.ReportUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static ru.zadvoryev.productionapp.util.ReportUtil.getReport;

/**
 * Отчеты
 */

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

    /**
     * @return список всех изделий без дубликатов
     */
    @ModelAttribute("distinct")
    public List<DistinctProductDto> getDistinctProductsList() {
        return recordService.getDistinctRecordsList();
    }

    /**
     * @return список всех линий
     */
    @ModelAttribute("lines")
    public List<LineDto> getLinesList() {
        return lineService.list();
    }

    /**
     * Отчет за период от start до end
     *
     * @param start - начальная дата
     * @param end   - конечная дата
     * @param model
     * @return записи от начальной даты до конечной
     */

    @GetMapping("/report-for-time")
    public String report(@RequestParam(name = "start", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                         @RequestParam(name = "end", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                         @RequestParam(name = "type", required = false) String type,
                         Model model, HttpServletResponse response) throws IOException {

        if (start != null && end != null) {
            List<ReportForTimeDto> recordsForReport = recordService.getRecordsForReport(start, end);
            List<List<ReportForTimeDto>> report = getReport(recordsForReport);
            model.addAttribute("list", report);

            if (type != null && type.equals("excel")) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=report_all_lines.xlsx");
                ByteArrayInputStream stream = ExcelReportForTimeReport.toExcelFile(report, start, end);
                IOUtils.copy(stream, response.getOutputStream());
            }
        }

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "reports/amount-per-time-report";
    }

    /**
     * Скачивание файла report-all-lines.xlsx
     * @param start
     * @param end
     * @param response
     * @throws IOException
     */
    @GetMapping("/report-for-time/download-excel")
    public void downloadExcel(@RequestParam(name = "start", required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                              @RequestParam(name = "end", required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                              HttpServletResponse response) throws IOException {

        List<ReportForTimeDto> recordsForReport = recordService.getRecordsForReport(start, end);
        List<List<ReportForTimeDto>> report = getReport(recordsForReport);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=report_all_lines.xlsx");
        ByteArrayInputStream stream = ExcelReportForTimeReport.toExcelFile(report, start, end);
        IOUtils.copy(stream, response.getOutputStream());
    }


    /**
     * Отчет
     *
     * @param start
     * @param end
     * @param line
     * @param product
     * @param model
     * @return
     */

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
        return "reports/productivity-report";
    }
}
