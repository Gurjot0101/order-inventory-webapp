package com.app.services;

import java.util.List;

import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Store;

public interface IStoreService {
	public List<Store> getAllStores();

	public Store getStoreById(Integer id) throws ResourceNotFoundException;
}
