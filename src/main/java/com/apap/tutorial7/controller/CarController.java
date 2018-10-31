package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.service.CarService;
import com.apap.tutorial7.service.DealerService;

@RestController
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
/*	@RequestMapping(value="/car/add/{dealerId}", method=RequestMethod.GET)
	private String add(@PathVariable(value="dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		dealer.setListCar(new ArrayList<>());
		 	
		model.addAttribute("dealer",dealer);
		return "addCar";
	}*/
	
		

	
	/*@RequestMapping(value="/car/add",method=RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute CarModel car) {
		carService.addCar(car);
		return "add";
	}*/
	@PostMapping()
	private CarModel addCarSubmit(@RequestBody CarModel car) {
		System.out.println("masuk sini");
		System.out.println("woo"+ car.getDealer().getId());
		
		return carService.addCar(car);
	}
	
	@GetMapping(value="/{carId}")
	private CarModel viewCar(@PathVariable("carId") long carId, Model model) {
		CarModel car = carService.getCarDetailById(carId).get();
	
		return car;
	}
	
	@GetMapping()
	private List <CarModel> viewAllCar() {
		
		return carService.findAll();
	}
	
	@DeleteMapping(value="/delete/{carId}")
	private String deleteDealer(@PathVariable("carId") long carId, Model model) {
		
		CarModel car = carService.getCarDetailById(carId).get();
		carService.deleteCar(car);
		
		return "Car Has Been Deleted";
	}
	
	@PutMapping(value = "/{id}")
	private String updateCarSubmit(
			@PathVariable (value = "id") long id,
			@RequestParam("brand") Optional<String> brand,
			@RequestParam("type") Optional<String> type,
			@RequestParam("price") Optional<Long> price,
			@RequestParam("amount") Optional<Integer> amount,
			@RequestParam("dealerId") Optional<Long> dealerId) {
		CarModel car = (CarModel) carService.getCarDetailById(id).get();
		if (car.equals(null)) {
			return "Couldn't find your car";
		}
		if (brand.isPresent()) {
			car.setBrand(brand.get());
		}
		if (type.isPresent()) {
			car.setType(type.get());
		}
		if (price.isPresent()) {
			car.setPrice(price.get());
		}
		if (amount.isPresent()) {
			car.setAmount(amount.get());
		}
		if (dealerId.isPresent()) {
			DealerModel dealerModel = dealerService.getDealerDetailById(dealerId.get()).get();
			car.setDealer(dealerModel);
		}
		carService.updateData(car, id);
		return "Car Update Success";
		
	}
	
	
/*	@RequestMapping(value="/car/delete/{dealerId}/{carId}",method=RequestMethod.GET)
	private String deleteCarSubmit(@PathVariable(value="dealerId")Long dealerId, @PathVariable(value="carId")Long carId, Model model) {
		CarModel car = carService.findCarById(carId);
		carService.deleteCar(car);
		
		return "delete";
	}*/
	
/*	@RequestMapping(value="/car/delete", method=RequestMethod.POST)
	private String deleteCar(@ModelAttribute DealerModel dealer, Model model) {
		for(CarModel car : dealer.getListCar()) {
			carService.deleteCar(car);
		}
		return "delete";
	}*/
	
	
	@RequestMapping(value="/car/update/{carId}", method=RequestMethod.GET)
	private String updateCar(@PathVariable(value="carId") Long carId, Model model) {
		CarModel car = carService.findCarById(carId);
		model.addAttribute("car",car);
		
		return "updateCar";
	}
	
	@RequestMapping(value="/car/update/{carId}",method=RequestMethod.POST)
	private String updateCarSubmit(@PathVariable(value="carId") Long carId, @ModelAttribute CarModel carUpdate) {
		
		
		carService.updateData(carUpdate, carId);
		
		return "update";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"addRow"})
	public String addRow(@ModelAttribute DealerModel dealer, BindingResult bindingResult, Model model) {
		if (dealer.getListCar() == null) {
            dealer.setListCar(new ArrayList<CarModel>());
        }
		dealer.getListCar().add(new CarModel());
		
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params={"removeRow"})
	public String removeRow(@ModelAttribute DealerModel dealer, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
	    final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    dealer.getListCar().remove(rowId.intValue());
	    
	    model.addAttribute("dealer", dealer);
	    return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params={"save"})
	private String addCarSubmit(@ModelAttribute DealerModel dealer) {
		DealerModel dealerNow  = dealerService.getDealerDetailById(dealer.getId()).get();
		for(CarModel car: dealer.getListCar()) {
			car.setDealer(dealerNow);
			carService.addCar(car);
		}
		return "add";
	}
	
	
	
	

}
