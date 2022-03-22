package ru.zadvoryev.productionapp.util;

import org.springframework.stereotype.Component;
import ru.zadvoryev.productionapp.dto.ReportDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportUtil {
    /**
     * Отчет
     * @param recordsForReport список записей
     */

    public static List<List<ReportDto>> createReport(List<ReportDto> recordsForReport) {
        List<List<ReportDto>> result = new ArrayList<>();
        List<List<ReportDto>> list = new ArrayList<>(recordsForReport.stream()
                .collect(Collectors.groupingBy(record -> Collections.singletonList(record.getLineId()))).values());
        for (List<ReportDto> item : list) {
            List<List<ReportDto>> sort = new ArrayList<>(item.stream().collect(Collectors.groupingBy(record -> Arrays.asList(
                            record.getNamePr().trim().toLowerCase().replaceAll(" ", ""),
                            record.getVar().trim().toLowerCase().replaceAll(" ", ""),
                            record.getSide().trim().toLowerCase().replaceAll(" ", ""))))
                    .values());
            List<ReportDto> sum = sum(sort);
            result.add(sum);
        }
        return result;
    }

    /**
     * Метод получает на вход список и суммирует значения поля quantity
     */
    private static List<ReportDto> sum(List<List<ReportDto>> list) {

        List<ReportDto> sum = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<ReportDto> records = list.get(i);
            for (int j = 1; j < records.size(); j++) {
                records.get(0).setQuantity(records.get(0).getQuantity() + records.get(j).getQuantity());
            }
            sum.add(records.get(0));
        }
        return sum;
    }
}
