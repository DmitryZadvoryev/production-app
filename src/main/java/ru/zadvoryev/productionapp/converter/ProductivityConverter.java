package ru.zadvoryev.productionapp.converter;

import org.springframework.stereotype.Component;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.dto.ProductivityDto;

import java.time.LocalTime;

import static ru.zadvoryev.productionapp.util.ReportUtil.getAverageDailyOutput;

@Component
public class ProductivityConverter extends Converter<ProductivityDto, Record> {

    public ProductivityConverter() {
        super(ProductivityConverter::convertToDto, ProductivityConverter::convertToEntity);
    }

    private static ProductivityDto convertToEntity(Record record) {
        ProductivityDto productivity = new ProductivityDto();
        LocalTime startTime = record.getStartTime();
        LocalTime endTime = record.getEndTime();
        int quantity = record.getQuantity();
        productivity.setDate(record.getDate());
        productivity.setAvgQuantityPerHour(getAverageDailyOutput(startTime, endTime, quantity));
        return productivity;
    }

    private static Record convertToDto(ProductivityDto productivityDTO) {
        throw new UnsupportedOperationException();
    }

}
