package ru.zadvoryev.productionapp.util;

import org.springframework.stereotype.Component;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.dto.RecordDto;

@Component
public class RecordConverter extends Converter<RecordDto, Record> {

    static LineConverter lineConverter = new LineConverter();
    static UserConverter userConverter = new UserConverter();

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

        if (recordDto.getAuthor() == null) {
            record.setAuthor(null);
        } else {
            record.setAuthor(userConverter.convertFromDto(recordDto.getAuthor()));
        }
        if (recordDto.getLine() == null) {
            record.setLine(null);
        } else {
            record.setLine(lineConverter.convertFromDto(recordDto.getLine()));
        }

        return record;
    }

    private static RecordDto convertToDto(Record record) {

        RecordDto recordDto = new RecordDto();

        recordDto.setId(record.getId());
        recordDto.setDate(record.getDate());
        recordDto.setStartTime(record.getStartTime());
        recordDto.setEndTime(record.getEndTime());
        recordDto.setNameOfOrganization(record.getNameOfOrganization());
        recordDto.setNameOfProduct(record.getNameOfProduct());
        recordDto.setVariant(record.getVariant());
        recordDto.setSide(record.getSide());
        recordDto.setQuantity(record.getQuantity());

        if (record.getAuthor() == null) {
            recordDto.setAuthor(null);
        } else {
            recordDto.setAuthor(userConverter.convertFromEntity(record.getAuthor()));
        }
        if (record.getLine() == null) {
            recordDto.setLine(null);
        } else {
            recordDto.setLine(lineConverter.convertFromEntity(record.getLine()));
        }

        return recordDto;
    }
}
