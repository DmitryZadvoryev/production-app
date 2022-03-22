package ru.zadvoryev.productionapp.converter;

import org.springframework.stereotype.Component;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.dto.ReportDto;

@Component
public class ReportConverter extends Converter<ReportDto, Record> {

    public ReportConverter() {
        super(ReportConverter::convertToDto, ReportConverter::convertToEntity);
    }

    private static ReportDto convertToEntity(Record record) {
        ReportDto reportDto = new ReportDto(record);
        return reportDto;
    }

    private static Record convertToDto(ReportDto reportDto) {
        throw new UnsupportedOperationException();
    }

}
