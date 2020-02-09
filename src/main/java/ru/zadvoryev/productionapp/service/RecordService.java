package ru.zadvoryev.productionapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zadvoryev.productionapp.converter.ProductivityConverter;
import ru.zadvoryev.productionapp.converter.RecordConverter;
import ru.zadvoryev.productionapp.converter.ReportForTimeConverter;
import ru.zadvoryev.productionapp.data.Line;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.data.User;
import ru.zadvoryev.productionapp.dto.DistinctProductDto;
import ru.zadvoryev.productionapp.dto.ProductivityDto;
import ru.zadvoryev.productionapp.dto.RecordDto;
import ru.zadvoryev.productionapp.dto.ReportForTimeDto;
import ru.zadvoryev.productionapp.repository.LineRepository;
import ru.zadvoryev.productionapp.repository.RecordRepository;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class RecordService {

    final RecordRepository recordRepository;

    final LineRepository lineRepository;

    final ProductivityConverter productivityConverter;

    final ReportForTimeConverter reportForTimeConverter;

    final RecordConverter recordConverter;

    public RecordService(RecordConverter converter, RecordRepository recordRepository, LineRepository lineRepository, ProductivityConverter productivityConverter, ReportForTimeConverter reportForTimeConverter) {
        this.recordConverter = converter;
        this.recordRepository = recordRepository;
        this.lineRepository = lineRepository;
        this.productivityConverter = productivityConverter;
        this.reportForTimeConverter = reportForTimeConverter;
    }

    @Transactional
    public Page<RecordDto> list(long id, Pageable pageable) {
        try {
            Page<Record> records = recordRepository.getRecordsPageable(id, pageable);
            return recordConverter.createFromEntities(records, pageable);
        } catch (NoResultException e) {
            return Page.empty(pageable);
        }
    }

    @Transactional
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

    @Transactional
    public void update(RecordDto recordDto) {
        Record record = recordConverter.convertFromDto(recordDto);
        Record recordById = recordRepository.getRecordById(recordDto.getId());
        record.setAuthor(recordById.getAuthor());
        record.setLine(recordById.getLine());
        recordRepository.save(record);
    }

    public RecordDto getOne(long id) {
        Record record = recordRepository.getRecordById(id);
        return recordConverter.convertFromEntity(record);
    }

    @Transactional
    public void create(RecordDto recordDto, User author, long id) {

        Record record = recordConverter.convertFromDto(recordDto);
        Line line = lineRepository.getOne(id);
        record.setAuthor(author);
        record.setLine(line);
        recordRepository.save(record);
    }

    @Transactional
    public void delete(long id) {
        recordRepository.deleteById(id);
    }

    @Transactional
    public List<ProductivityDto> getRecordsForProductivityReport(LocalDate start,
                                                                 LocalDate end,
                                                                 long lineId,
                                                                 String nameOfProduction,
                                                                 String variant,
                                                                 String side) {
        try {
            List<Record> records = recordRepository.getRecordsForProductivityReport(start,
                    end,
                    lineId,
                    nameOfProduction,
                    variant,
                    side);
            return productivityConverter.createFromEntities(records);
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Transactional
    public List<DistinctProductDto> getDistinctRecordsList() {
        try {
            return recordRepository.getDistinctRecords();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Transactional
    public List<ReportForTimeDto> getRecordsForReport(LocalDate start, LocalDate end) {
        try {
            List<Record> recordsForReport = recordRepository.getRecordsBetweenDate(start, end);
            List<ReportForTimeDto> fromEntities = reportForTimeConverter.createFromEntities(recordsForReport);
            return fromEntities;
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
