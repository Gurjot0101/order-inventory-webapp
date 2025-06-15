package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer>{
	
}
