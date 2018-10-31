package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.repository.DealerDb;

public interface DealerService {
	Optional<DealerModel> getDealerDetailById(Long id);
	DealerModel addDealer (DealerModel dealer);
	/*void deleteDealer (DealerModel dealer);*/
	List<DealerModel> allDealer();
	void deleteDealer(DealerModel dealer);
	void updateDealer(DealerModel updateDealer, Long dealerId);
	
	
}
