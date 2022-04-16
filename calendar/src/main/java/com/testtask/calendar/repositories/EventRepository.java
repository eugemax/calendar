package com.testtask.calendar.repositories;

import com.testtask.calendar.domain.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends CrudRepository<Event,Long> {

    //List<Event> findByTitle(String title);

     void deleteById(Integer id);

     @Query(value = "SELECT * FROM Event WHERE DATE_PART('Year',start)=?1",nativeQuery = true)
    List<Event> findByYear(Integer year);

    @Query(value = "SELECT * FROM Event WHERE DATE_PART('Mon',start)= ?1",nativeQuery = true)
    List<Event> findByMonth(Integer month);

    @Query(value = "SELECT * FROM Event WHERE DATE_PART('Day',start)= ?1 and DATE_PART('Mon',start)= ?2  and DATE_PART('Year',start)= ?3",nativeQuery = true)
    List<Event> findByDate(Integer day, Integer month, Integer year);


}
