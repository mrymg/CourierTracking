package com.sh.migros.controller;


import com.sh.migros.model.Store;
import com.sh.migros.service.StoreService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/getAllStores")
    public Iterable<Store> listStores(){
        return storeService.getAllStores() ;
    }
}
