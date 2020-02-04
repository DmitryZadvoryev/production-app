package ru.zadvoryev.productionapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zadvoryev.productionapp.data.Line;

import java.util.List;

@Repository
public interface LineRepository extends CrudRepository<Line, Long> {

    @Query(value = "select * from line where id = ?1", nativeQuery = true)
    Line getOne(Long id);

    @Query(value = "select * from Line order by id", nativeQuery = true)
    List<Line> findAll();

}
