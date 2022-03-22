package ru.zadvoryev.productionapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zadvoryev.productionapp.converter.RecordConverter;
import ru.zadvoryev.productionapp.converter.ReportConverter;
import ru.zadvoryev.productionapp.data.Line;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.data.User;
import ru.zadvoryev.productionapp.dto.RecordDto;
import ru.zadvoryev.productionapp.dto.ReportDto;
import ru.zadvoryev.productionapp.repository.LineRepository;
import ru.zadvoryev.productionapp.repository.RecordRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecordService {

    final RecordRepository recordRepository;

    final LineRepository lineRepository;

    final ReportConverter reportConverter;

    final RecordConverter recordConverter;

    public RecordService(RecordConverter converter,
                         RecordRepository recordRepository,
                         LineRepository lineRepository,
                         ReportConverter reportConverter) {
        this.recordConverter = converter;
        this.recordRepository = recordRepository;
        this.lineRepository = lineRepository;
        this.reportConverter = reportConverter;
    }

    public Page<RecordDto> list(long id, Pageable pageable) {
        Page<Record> records = recordRepository.getRecordsPageable(id, pageable);
        return recordConverter.createFromEntities(records, pageable);
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
    }

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

    public void create(RecordDto recordDto, User author, long id) {
        Record record = recordConverter.convertFromDto(recordDto);
        Line line = lineRepository.getOne(id);
        record.setAuthor(author);
        record.setLine(line);
        recordRepository.save(record);
    }

    public void delete(long id) {
        recordRepository.deleteById(id);
    }

    public List<ReportDto> getRecords(LocalDate start, LocalDate end) {
        List<Record> recordsForReport = recordRepository.getRecordsBetweenDate(start, end);
        return reportConverter.createFromEntities(recordsForReport);
    }
}
