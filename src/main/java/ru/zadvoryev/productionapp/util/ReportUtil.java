package ru.zadvoryev.productionapp.util;

import org.springframework.stereotype.Service;
import ru.zadvoryev.productionapp.dto.ProductivityDto;
import ru.zadvoryev.productionapp.dto.ReportForTimeDto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportUtil {

    public static List<List<ReportForTimeDto>> getReport(List<ReportForTimeDto> recordsForReport) {
        List<List<ReportForTimeDto>> result = new ArrayList<>();
        List<List<ReportForTimeDto>> list = recordsForReport.stream()
                .collect(Collectors.groupingBy(record -> Arrays.asList(record.getLineId()))).values()
                .stream().collect(Collectors.toList());
        for (List<ReportForTimeDto> item : list) {
            List<List<ReportForTimeDto>> sort = item.stream().collect(Collectors.groupingBy(record -> Arrays.asList(
                    record.getManePr(),
                    record.getVar(),
                    record.getSide())))
                    .values().stream().collect(Collectors.toList());

            List<ReportForTimeDto> sum = sum(sort);
            result.add(sum);
        }
        return result;
    }

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

    public static float getAverageDailyOutput(LocalTime start, LocalTime end, int quantity) {
        LocalTime time = end.minusHours(start.getHour()).withMinute(start.getMinute());
        float minutes = time.getHour() * 60.0f + time.getMinute();
        float result = quantity / minutes * 60;
        return result;
    }

    public static float getPeriodAvg(List<ProductivityDto> records) {
        float sum = 0;
        for (ProductivityDto record : records) {
            sum = +record.getAvgQuantityPerHour();
        }
        return sum;
    }

}
