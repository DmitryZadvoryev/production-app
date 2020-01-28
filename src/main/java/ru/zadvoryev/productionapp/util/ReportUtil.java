package ru.zadvoryev.productionapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.dto.ProductivityDto;
import ru.zadvoryev.productionapp.dto.ReportForTimeDto;
import ru.zadvoryev.productionapp.repository.RecordRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReportUtil {


    public static List<List<ReportForTimeDto>> getReportForTime(LocalDate start, LocalDate end, List<Long> allId, RecordRepository recordRepository) {
        List<List<ReportForTimeDto>> results = new ArrayList<>();
        List<List<ReportForTimeDto>> sorted = new ArrayList<>();
        List<ReportForTimeDto> records;
        for (int i = 0; i < allId.size(); i++) {
            List<Record> recordsForReport = recordRepository.getRecordsForReport(allId.get(i), start, end);
            records = ReportUtil.convertToReportForTimeDTO(recordsForReport);
            while (!records.isEmpty()) {
                sorted.add(sort(records));
            }
            results.add(sum(sorted));
        }
        records =null;
        sorted = null;
        return results;
    }



    public static List<ReportForTimeDto> convertToReportForTimeDTO(List<Record> records) {
        List<ReportForTimeDto> list = new ArrayList<>();
        for (Record record : records) {
            ReportForTimeDto reportForTimeDTO = new ReportForTimeDto(record);
            list.add(reportForTimeDTO);
        }
        return list;
    }


    public static List<ReportForTimeDto> sort(List<ReportForTimeDto> products) {

        List<ReportForTimeDto> result = new LinkedList<>();

        if (result.isEmpty()) {
            result.add(products.get(0));
            products.remove(0);
        }
        for (int i = 0; i < products.size(); i++) {
            if ((result.get(0).getVar().equals(products.get(i).getVar())
                    && (result.get(0).getSide().equals(products.get(i).getSide()))
                    && products.get(0).getManePr().equals(products.get(i).getManePr()))) {
                result.add(products.get(i));
                products.remove(i);
                i--;
            }
        }

        return result;
    }

    public static List<ReportForTimeDto> sum(List<List<ReportForTimeDto>> products) {
        List<ReportForTimeDto> sum = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            List<ReportForTimeDto> products1 = products.get(i);
            for (int j = 1; j < products1.size(); j++) {
                products1.get(0).setQuantity(products1.get(0).getQuantity() + products1.get(j).getQuantity());
            }
            sum.add(products1.get(0));
        }
        products.clear();
        return sum;
    }

    public static float getAverageDailyOutput(LocalTime start, LocalTime end, int quantity){
        LocalTime time = end.minusHours(start.getHour()).withMinute(start.getMinute());
        float minutes = time.getHour() * 60.0f + time.getMinute();
        float result = quantity/minutes * 60;
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
