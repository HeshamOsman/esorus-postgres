package com.esorus.api.web.rest;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.esorus.api.security.AuthoritiesConstants;
import com.esorus.api.service.UploadsService;
import com.esorus.api.service.util.FileInfoVM;

@RestController
@RequestMapping("/api")
public class UploadsResource {

	@Autowired
	private UploadsService uploadsService;
	
	@PostMapping("/upload-files")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.PROFESSIONAL_BUYER + "\")")
	public ResponseEntity<FileInfoVM> saveTempraryFile(@RequestParam("file") MultipartFile file) {

		try {
			Optional<FileInfoVM> uploadServiceResponse = uploadsService.saveTemporaryFile(file, "buyer_request");
			if (uploadServiceResponse.isPresent())
				return ResponseEntity.ok(uploadServiceResponse.get());
			else
				return ResponseEntity.badRequest().build();
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
