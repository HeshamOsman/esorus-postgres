package com.esorus.api.web.rest;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esorus.api.domain.RequestForSupplier;
import com.esorus.api.security.AuthoritiesConstants;
import com.esorus.api.service.MailService;
import com.esorus.api.service.RequestForSupplierService;
import com.esorus.api.service.dto.RequestForSupplierDTO;

@RestController
@RequestMapping("/api")
public class RequestForSupplierResource {

	@Autowired
	private RequestForSupplierService requestForSupplierService;
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("/request-for-supplier")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.PROFESSIONAL_BUYER + "\")")
	public ResponseEntity<RequestForSupplier> saveTempraryFile(@Valid @RequestBody RequestForSupplierDTO rosDto) {

			Optional<RequestForSupplier> uploadServiceResponse = requestForSupplierService.save(rosDto);
			if (uploadServiceResponse.isPresent()) {
				mailService.sendBuyerRequestSubmittedEmail(uploadServiceResponse.get());
				return ResponseEntity.ok(uploadServiceResponse.get());
			}else
				return ResponseEntity.badRequest().build();
		
	}
}
