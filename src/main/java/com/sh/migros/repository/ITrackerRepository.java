package com.sh.migros.repository;

import com.sh.migros.model.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Repository
public interface ITrackerRepository extends JpaRepository<Tracker, Integer> {
    Iterable<Tracker> findTrackersByCourierId(Integer courierID);

    Tracker findTrackerByCourierIdAndStoreIdAndTimeGreaterThan(Integer courierId, Integer storeID, Date time );
}
