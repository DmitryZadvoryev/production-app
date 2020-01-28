package ru.zadvoryev.productionapp.util;

import org.springframework.stereotype.Component;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.dto.RecordDto;

@Component
public class RecordConverter extends Converter<RecordDto, Record> {

    public RecordConverter() {
        super(RecordConverter::convertToEntity, RecordConverter::convertToDto);
    }

    private static Record convertToEntity(RecordDto recordDto) {
        Record record = new Record();
        record.setId(recordDto.getId());
        record.setDate(recordDto.getDate());
        record.setStartTime(recordDto.getStartTime());
        record.setEndTime(recordDto.getEndTime());
        record.setNameOfOrganization(recordDto.getNameOfOrganization());
        record.setNameOfProduct(recordDto.getNameOfProduct());
        record.setVariant(recordDto.getVariant());
        record.setSide(recordDto.getSide());
        record.setQuantity(recordDto.getQuantity());
        return record;
        // throw new UnsupportedOperationException();
    }

    private static RecordDto convertToDto(Record record) {
        return new RecordDto(record.getId(),
                record.getDate(),
                record.getStartTime(),
                record.getEndTime(),
                record.getNameOfOrganization(),
                record.getNameOfProduct(),
                record.getVariant(),
                record.getSide(),
                record.getQuantity(),
                record.getAuthor().getId(),
                record.getAuthor().getName(),
                record.getAuthor().getSurname());

    }
}
