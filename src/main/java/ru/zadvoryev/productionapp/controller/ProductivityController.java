package ru.zadvoryev.productionapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.zadvoryev.productionapp.dto.DistinctProductDto;
import ru.zadvoryev.productionapp.dto.LineDto;
import ru.zadvoryev.productionapp.dto.ProductivityDto;
import ru.zadvoryev.productionapp.service.LineService;
import ru.zadvoryev.productionapp.service.RecordService;
import ru.zadvoryev.productionapp.util.ReportUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ProductivityController {

    @Autowired
    RecordService recordService;

    @Autowired
    LineService lineService;

    @ModelAttribute("distinct")
    public List<DistinctProductDto> getDistinctRecordsList() {
        return recordService.getDistinctRecordsList();
    }

    @ModelAttribute("lines")
    public List<LineDto> getLinesList() {
        return lineService.list();
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
