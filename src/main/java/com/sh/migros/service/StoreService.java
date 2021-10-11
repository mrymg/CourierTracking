package com.sh.migros.service;

import com.sh.migros.model.Store;
import com.sh.migros.repository.IStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final IStoreRepository storeRepository;

    public StoreService(IStoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    public Store save(Store store){
        return storeRepository.save(store);
    }

    public Iterable<Store> saveAll(List<Store> stores){
        return storeRepository.saveAll(stores);
    }

    public Iterable<Store> getAllStores(){
        return storeRepository.findAll();
    }

}
