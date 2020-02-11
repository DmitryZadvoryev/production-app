package ru.zadvoryev.productionapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zadvoryev.productionapp.data.Record;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Record getRecordById(long id);

    @Query(value = "select record.* from record where line_id = ?1 order by record.id desc", nativeQuery = true)
    Page<Record> getRecordsPageable(Long id, Pageable pageable);

    @Query(value = "select record.* from record where " +
            "record.date between  ?1 and ?2", nativeQuery = true)
    List<Record> getRecordsBetweenDate(LocalDate start,
                                       LocalDate end);


    /**
     * Фильтр, поиск записей по параметрам
     *
     * @param id                 - ид линии
     * @param start              - нач дата
     * @param end                - кон дата
     * @param nameOfOrganization - наименование организации
     * @param nameOfProduct      - наименования изделия
     * @param variant            - вариант исполнения
     * @param side               - сторона изделия
     * @param surname            - фамилия исполнителя
     * @param pageable           - пагинация
     * @return постраничный список
     */
    @Query(value = "select record.* from record" +
            " left join usr on record.author_id = usr.id" +
            " where record.line_id = ?1" +
            " and record.date between ?2 and ?3" +
            " and record.name_of_organization like %?4%" +
            " and record.name_of_product like %?5%" +
            " and record.variant like %?6%" +
            " and record.side like %?7%" +
            " and usr.surname like %?8%" +
            " order by record.id desc" +
            " \n-- #pageable\n ", nativeQuery = true)
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

}
