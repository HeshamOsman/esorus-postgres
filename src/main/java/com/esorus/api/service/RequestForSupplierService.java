package com.esorus.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esorus.api.domain.ProjectPhase;
import com.esorus.api.domain.ProjectType;
import com.esorus.api.domain.RequestForSupplier;
import com.esorus.api.domain.TypeOfWorkNeeded;
import com.esorus.api.domain.Uploads;
import com.esorus.api.domain.User;
import com.esorus.api.repository.ProjectPhaseRepository;
import com.esorus.api.repository.ProjectTypeRepository;
import com.esorus.api.repository.RequestForSupplierRepository;
import com.esorus.api.repository.TypeOfWorkNeededRepository;
import com.esorus.api.repository.UploadsRepository;
import com.esorus.api.repository.UserRepository;
import com.esorus.api.security.SecurityUtils;
import com.esorus.api.service.dto.RequestForSupplierDTO;
import com.esorus.api.service.util.FileInfoVM;

@Service
@Transactional
public class RequestForSupplierService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RequestForSupplierRepository requestForSupplierRepository;
	
	@Autowired
	private UploadsRepository uploadsRepository;
	
	@Autowired
	private ProjectPhaseRepository projectPhaseRepository;
	
	@Autowired
	private ProjectTypeRepository projectTypeRepository;
	
	@Autowired
	private TypeOfWorkNeededRepository typeOfWorkNeededRepository;
	
	public Optional<RequestForSupplier> save(RequestForSupplierDTO requestForSupplierDTO){
		Optional<RequestForSupplier> tempraryUploadResponse = Optional.empty();

		User currentUser = userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get())
				.get();
		if (currentUser == null) {
			return tempraryUploadResponse;
		}
		
		RequestForSupplier requestForSupplier = new RequestForSupplier();
		requestForSupplier.setBoq(requestForSupplierDTO.getBoq());
		requestForSupplier.setDeliveryDate(requestForSupplierDTO.getDeliveryDate());
		requestForSupplier.setDetailes(requestForSupplierDTO.getDetailes());
		requestForSupplier.setPhoneNumber(requestForSupplierDTO.getPhoneNumber());
		Optional<ProjectPhase> pp= projectPhaseRepository.findOneBySlug(requestForSupplierDTO.getProjectPhase());
		Optional<ProjectType> pt= projectTypeRepository.findOneBySlug(requestForSupplierDTO.getProjectType());
		Optional<TypeOfWorkNeeded> town= typeOfWorkNeededRepository.findOneBySlug(requestForSupplierDTO.getTypeOfWorkNeeded());
		Optional<Uploads> pic = uploadsRepository.findOneBySavedFileName(requestForSupplierDTO.getUploadedPic());
		if(pic.isPresent()&&!pic.get().getUser().getEmail().equals(currentUser.getEmail()))
			return tempraryUploadResponse;
		if(!pp.isPresent())
			return tempraryUploadResponse;
		if(!pt.isPresent())
			return tempraryUploadResponse;
		if(!town.isPresent())
			return tempraryUploadResponse;
		requestForSupplier.setProjectPhase(pp.get());
		requestForSupplier.setProjectType(pt.get());
		requestForSupplier.setQuantity(requestForSupplierDTO.getQuantity());
		requestForSupplier.setTypeOfWorkNeeded(town.get());
		requestForSupplier.setUploadedPic(pic.isPresent()?pic.get():null);
		requestForSupplier.setUser(currentUser);
		requestForSupplier = requestForSupplierRepository.save(requestForSupplier);
		tempraryUploadResponse = Optional.of(requestForSupplier);
		return tempraryUploadResponse;
		
	}
}
