package ru.zadvoryev.productionapp.util;

import org.springframework.stereotype.Component;
import ru.zadvoryev.productionapp.dto.ReportForTimeDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportUtil {
    /**
     * Отчет
     * @param recordsForReport список записей
     */

    public static List<List<ReportForTimeDto>> getReport(List<ReportForTimeDto> recordsForReport) {
        List<List<ReportForTimeDto>> result = new ArrayList<>();
        List<List<ReportForTimeDto>> list = recordsForReport.stream()
                .collect(Collectors.groupingBy(record -> Arrays.asList(record.getLineId()))).values()
                .stream().collect(Collectors.toList());
        for (List<ReportForTimeDto> item : list) {
            List<List<ReportForTimeDto>> sort = item.stream().collect(Collectors.groupingBy(record -> Arrays.asList(
                    record.getNamePr().trim().toLowerCase().replaceAll(" ", ""),
                    record.getVar().trim().toLowerCase().replaceAll(" ", ""),
                    record.getSide().trim().toLowerCase().replaceAll(" ", ""))))
                    .values().stream().collect(Collectors.toList());

            List<ReportForTimeDto> sum = sum(sort);
            result.add(sum);
        }
        return result;
    }

    /**
     * Метод получает на вход список и суммирует значения поля quantity
     */
    private static List<ReportForTimeDto> sum(List<List<ReportForTimeDto>> list) {
        List<ReportForTimeDto> sum = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<ReportForTimeDto> records = list.get(i);
            for (int j = 1; j < records.size(); j++) {
                records.get(0).setQuantity(records.get(0).getQuantity() + records.get(j).getQuantity());
            }
            sum.add(records.get(0));
        }
        return sum;
    }
}
