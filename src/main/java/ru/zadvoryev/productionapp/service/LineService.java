package ru.zadvoryev.productionapp.service;

import org.springframework.stereotype.Service;
import ru.zadvoryev.productionapp.data.Line;
import ru.zadvoryev.productionapp.dto.LineDto;
import ru.zadvoryev.productionapp.repository.LineRepository;
import ru.zadvoryev.productionapp.util.Converter;
import ru.zadvoryev.productionapp.util.LineConverter;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Service
public class LineService {

    final LineRepository lineRepository;

    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    Converter<LineDto, Line> converter = new LineConverter();

    public List<LineDto> list() {
        try {
            List<Line> all = lineRepository.findAll();
            return converter.createFromEntities(all);
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public LineDto getOne(long id) {
        try {
            Line line = lineRepository.getOne(id);
            return converter.convertFromEntity(line);
        } catch (NoResultException e) {
            return null;
        }
    }

    public void createOrUpdate(LineDto lineDto) {
        Line line = converter.convertFromDto(lineDto);
        lineRepository.save(line);
    }

    public void delete(long id) {
        lineRepository.deleteById(id);
    }

}
