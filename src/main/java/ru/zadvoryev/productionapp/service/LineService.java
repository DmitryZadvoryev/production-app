package ru.zadvoryev.productionapp.service;

import org.springframework.stereotype.Service;
import ru.zadvoryev.productionapp.converter.LineConverter;
import ru.zadvoryev.productionapp.data.Line;
import ru.zadvoryev.productionapp.dto.LineDto;
import ru.zadvoryev.productionapp.repository.LineRepository;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Service
public class LineService {

    final LineRepository lineRepository;

    final LineConverter converter;

    public LineService(LineRepository lineRepository, LineConverter converter) {
        this.lineRepository = lineRepository;
        this.converter = converter;
    }

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
