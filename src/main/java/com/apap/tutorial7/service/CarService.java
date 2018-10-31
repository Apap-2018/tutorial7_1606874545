package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.CarModel;

public interface CarService {
	CarModel addCar(CarModel car);
	void deleteCar(CarModel car);
	CarModel findCarById(Long id);
	
	
	void updateData(CarModel updateCarData, Long carId);
	List<CarModel> sortByPrice(Long dealer_id);
	Optional<CarModel> getCarDetailById(Long id);
	List<CarModel> findAll();
}
