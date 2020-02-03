package ru.zadvoryev.productionapp.converter;

import org.springframework.stereotype.Component;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.dto.ReportForTimeDto;

@Component
public class ReportForTimeConverter extends Converter<ReportForTimeDto, Record> {

    public ReportForTimeConverter() {
        super(ReportForTimeConverter::convertToDto, ReportForTimeConverter::convertToEntity);
    }

    private static ReportForTimeDto convertToEntity(Record record) {
        ReportForTimeDto reportForTimeDto = new ReportForTimeDto(record);
        return reportForTimeDto;
    }

    private static Record convertToDto(ReportForTimeDto reportForTimeDto) {
        throw new UnsupportedOperationException();
    }

}
