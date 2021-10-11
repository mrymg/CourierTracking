package com.sh.migros.controller;

import com.sh.migros.model.Courier;
import com.sh.migros.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/courier")
public class AppController {

    @Autowired
    private CourierService courierService;

    /**COURIER OPERATIONS:
        1- ADD COURIER
        2- CHECK DISTANCE FROM STORE
        3- CHECK IF RE ENTRANCE EXIST
     */
    @PostMapping
    public void courierOp(@RequestBody Courier courier){
        courierService.courierOperations(courier);
    }

    //TO GET COURIER TOTAL TRAVEL DISTANCE
    @GetMapping("/totalTravelDistance/{id}")
    public double getTotalTravelDistance(@PathVariable Integer id){
       return courierService.getTotalTravelDistance(id);
    }

}
