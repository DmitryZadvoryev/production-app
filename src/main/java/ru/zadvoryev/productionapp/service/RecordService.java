package ru.zadvoryev.productionapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zadvoryev.productionapp.data.Line;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.data.User;
import ru.zadvoryev.productionapp.dto.DistinctProductDto;
import ru.zadvoryev.productionapp.dto.ProductivityDto;
import ru.zadvoryev.productionapp.dto.RecordDto;
import ru.zadvoryev.productionapp.repository.LineRepository;
import ru.zadvoryev.productionapp.repository.RecordRepository;
import ru.zadvoryev.productionapp.util.ProductivityConverter;
import ru.zadvoryev.productionapp.util.RecordConverter;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    LineRepository lineRepository;

    @Autowired
    ProductivityConverter productivityConverter;

    final RecordConverter recordConverter;

    public RecordService(RecordConverter converter) {
        this.recordConverter = converter;
    }

    public Page<RecordDto> list(long id, Pageable pageable) {
        try {
            Page<Record> records = recordRepository.getRecordsPageable(id, pageable);
            return recordConverter.createFromEntities(records, pageable);
        } catch (NoResultException e) {
            return Page.empty(pageable);
        }
    }

    public Page<RecordDto> filter(long id,
                                  LocalDate start,
                                  LocalDate end,
                                  String nameOfOrganization,
                                  String nameOfProduct,
                                  String variant,
                                  String side,
                                  String surname,
                                  Pageable pageable) {
        try {
            Page<Record> records = recordRepository.filter(id,
                    start,
                    end,
                    nameOfOrganization,
                    nameOfProduct,
                    variant,
                    side,
                    surname,
                    pageable);
            return recordConverter.createFromEntities(records, pageable);
        } catch (NoResultException e) {
            return Page.empty(pageable);
        }
    }

    public void udpate(RecordDto recordDto) {
        Record record = recordConverter.convertFromDto(recordDto);
        recordRepository.save(record);
    }

    public RecordDto getOne(long id) {
        Record record = recordRepository.getRecordById(id);
        return recordConverter.convertFromEntity(record);
    }

    public void create(RecordDto recordDto, User author, long id) {
        Record record = recordConverter.convertFromDto(recordDto);
        Line line = lineRepository.getOne(id);
        record.setAuthor(author);
        record.setLine(line);
        recordRepository.save(record);
    }

    public List<ProductivityDto> getRecordsForProductivityReport(LocalDate start, LocalDate end, long lineId,
                                                                 String nameOfProduction, String variant, String side) {
        try {
            List<Record> records = recordRepository.getRecordsForProductivityReport(start, end, lineId, nameOfProduction,
                    variant, side);
            return productivityConverter.createFromEntities(records);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<DistinctProductDto> getDistinctRecordsList() {
        try {
            return recordRepository.getDistinctRecords();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
