package com.carrental.carrental.repo;

import com.carrental.carrental.model.StatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StatusLogRepo extends JpaRepository<StatusLog, Long> {

    @Query(value = "SELECT plate_id, max_date as last_date, status FROM (SELECT s.plate_id, s.date, MAX(s.date) OVER (PARTITION BY s.plate_id) AS max_date, s.status FROM status_log AS s WHERE s.date <= ?1) AS sub_table WHERE sub_table.date = sub_table.max_date",nativeQuery = true)
    List<Object[]> getAllStates(Date date);
}
