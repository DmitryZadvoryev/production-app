package ru.zadvoryev.productionapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.zadvoryev.productionapp.data.Record;
import ru.zadvoryev.productionapp.dto.DistinctProductDto;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface RecordRepository extends CrudRepository<Record, Long> {

    Record getRecordById(long id);

    @Query(value = "select record.* from record where line_id = ?1", nativeQuery = true)
    Page<Record> getRecordsPageable(Long id, Pageable pageable);

    @Query(value = "select record.* from record where line_id = ?1 " +
            "and record.date between  ?2 and ?3", nativeQuery = true)
    List<Record> getRecordsForReport(Long id, LocalDate start,
                                     LocalDate end);

    @Query(value = "select record.* from record " +
            "left join usr on record.author_id = usr.id" +
            " where record.line_id = ?1" +
            " and record.date between ?2 and ?3" +
            " and record.name_of_organization like %?4%" +
            " and record.name_of_product like %?5%" +
            " and record.variant like %?6%" +
            " and record.side like %?7%" +
            " and usr.surname like %?8%" +
            " \n-- #pageable\n",
            countQuery = "select count(*) from line",
            nativeQuery = true)
    Page<Record> filter(
            long id,
            LocalDate start,
            LocalDate end,
            String nameOfOrganization,
            String nameOfProduct,
            String variant,
            String side,
            String surname,
            Pageable pageable
    );

    @Query(value = "select distinct new ru.zadvoryev.productionapp.dto.DistinctProductDto(" +
            "record.nameOfProduct, record.variant, record.side) from Record record")
    List<DistinctProductDto> getDistinctRecords();

    @Query(value = "select record.* from record where record.date between ?1 and ?2 and record.line_id = ?3 and record.name_of_product = ?4 " +
            "and record.variant = ?5 and record.side = ?6", nativeQuery = true)
    List<Record> getRecordsForProductivityReport(LocalDate start, LocalDate end, long lineId, String nameOfProduction,
                                                 String variant, String side);


}
