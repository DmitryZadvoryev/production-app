package ru.zadvoryev.productionapp.converter;

import org.springframework.stereotype.Component;
import ru.zadvoryev.productionapp.data.Line;
import ru.zadvoryev.productionapp.dto.LineDto;

@Component
public class LineConverter extends Converter<LineDto, Line> {

    public LineConverter() {
        super(LineConverter::convertToEntity, LineConverter::convertToDto);
    }

    private static LineDto convertToDto(Line line) {
        return new LineDto(line.getId(), line.getName(), line.getRecords());
    }

    private static Line convertToEntity(LineDto lineDto) {
        Line line = new Line();
        line.setId(lineDto.getId());
        line.setName(lineDto.getName());
        line.setRecords(lineDto.getRecords());
        return line;
    }
}
