package com.sh.migros.service;

import com.sh.migros.helpers.GeoLocationHelper;
import com.sh.migros.model.Courier;
import com.sh.migros.model.Store;
import com.sh.migros.model.Tracker;
import com.sh.migros.repository.ICourierRepository;
import com.sh.migros.repository.ITrackerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CourierService {
    private final ICourierRepository courierRepository;
    private final StoreService storeService;
    private final Logger logger = LoggerFactory.getLogger(CourierService.class);
    private final GeoLocationHelper distanceHelper;
    private final ITrackerRepository trackerRepository;



    public CourierService(ICourierRepository courierRepository, StoreService storeService, GeoLocationHelper distanceHelper, ITrackerRepository trackerRepository) {
        this.courierRepository = courierRepository;
        this.storeService = storeService;
        this.distanceHelper = distanceHelper;
        this.trackerRepository = trackerRepository;
    }


    public Courier save(Courier courier){
        return courierRepository.save(courier);
    }
    public Iterable<Courier> getAllCouriers(Courier courier){
        return courierRepository.findAll();
    }

    private boolean isCourierExist(Courier courier){
        return courierRepository.findCourierByCourierId(courier.getCourierId()) != null;
    }

    public void courierOperations(Courier courier){
        if(isCourierExist(courier)){
            nearStoreCheck(courier);
            logger.info("COURIER LOCATIONS UPDATED");
        }else{
            logger.info("COURIER ADDED");
            courierRepository.save(courier);
            nearStoreCheckNoCourier(courier);
        }
    }

    //IF COURIER EXIST IN DB, THIS FUNCTION WILL BE WORKED.
    public void nearStoreCheck(Courier courier){
        Iterable<Store> allStores = storeService.getAllStores();
        Date date = new Date();
        long aMinuteBefore = date.getTime() - (60*1000);
        Date aMinuteBeforeDate = new Date(aMinuteBefore);
        allStores.forEach(store -> {
            double distance = distanceHelper.distanceBetweenTwoLocations(store.getLat(), store.getLng(), courier.getLat(), courier.getLng());
            if(distance <100.0){
                if (isReentrance(courier.getCourierId(), store.getId(), aMinuteBeforeDate)){
                    logger.warn("REENTRANCE DETECTED. NOT SAVED.");
                }else{
                    Timestamp time = new Timestamp(date.getTime());
                    Tracker tracker = new Tracker();
                    tracker.setCourierId(courier.getCourierId());
                    tracker.setStoreId(store.getId());
                    tracker.setTime(time);
                    logger.warn("ENTRANCE COURIER: "+ courier.getCourierId().toString() + " STORE: "+ store.getName()+ "|| WITH DISTANCE : "+ distance);
                    trackerRepository.save(tracker);
                }

            }
            double courierDistance = distanceHelper.distanceBetweenTwoLocations(
                    courierRepository.findCourierByCourierId(courier.getCourierId()).getLat(),
                    courierRepository.findCourierByCourierId(courier.getCourierId()).getLng(),
                    courier.getLat(),
                    courier.getLng());
            courier.setTotalTravelDistance(courierRepository.findCourierByCourierId(courier.getCourierId()).getTotalTravelDistance() + courierDistance);
            courierRepository.save(courier);
        });
    }


    // IF NO COURIER IN DB, THIS FUNCTION WILL BE WORKED.
    public void nearStoreCheckNoCourier(Courier courier){
        Iterable<Store> allStores = storeService.getAllStores();
        allStores.forEach(store -> {
            double distance = distanceHelper.distanceBetweenTwoLocations(store.getLat(), store.getLng(), courier.getLat(), courier.getLng());
            if(distance <100.0){
                Date date = new Date();
                Timestamp time = new Timestamp(date.getTime());
                Tracker tracker = new Tracker();
                tracker.setCourierId(courier.getCourierId());
                tracker.setStoreId(store.getId());
                tracker.setTime(time);
                logger.warn("ENTRANCE COURIER: "+ courier.getCourierId().toString() + " STORE: "+ store.getName()+ " W/// "+ distance);
                trackerRepository.save(tracker);

            }
        });
    }


    // THIS FUNCTION DETECTS IF RE ENTRANCE IS EXIST
    private boolean isReentrance(Integer courierId, Integer storeId, Date time){
        return trackerRepository.findTrackerByCourierIdAndStoreIdAndTimeGreaterThan(courierId, storeId, time) != null;
    }

    // GETTING COURIER TOTAL DISTANCE FROM COURIER TABLE IN DB.
    public double getTotalTravelDistance(Integer courierId){
        double courierTotalDistance = courierRepository.findCourierByCourierId(courierId).getTotalTravelDistance();
        logger.info("COURIER TOTAL DISTANCE IS: " + courierTotalDistance);
        return courierTotalDistance;
    }

}
