package com.app.services.impl;

import java.util.List;

import com.app.services.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.StoreRepository;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Store;

// Service class for handling business logic related to stores
@Service
public class StoreService implements IStoreService {

    @Autowired
    private StoreRepository storeRepository; // DAO for interacting with the store database

    // Retrieve all stores from the database
    @Override
    public List<Store> getAllStores() {
        System.out.println(storeRepository.findAll()); // Print all stores for debugging purposes
        return storeRepository.findAll(); // Fetch and return all stores from the repository
    }

    // Retrieve a store by its ID
    @Override
    public Store getStoreById(Integer id) throws ResourceNotFoundException {
        // Find the store by ID or throw an exception if not found
        return storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }
}
