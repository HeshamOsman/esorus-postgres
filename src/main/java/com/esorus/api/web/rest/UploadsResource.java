package com.esorus.api.web.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/upload-files/{name}")
	public ResponseEntity<Resource> viewTempraryFile(@PathVariable("name") String fileName)  {

		
		
			Optional<FileInfoVM> uploadServiceResponse = uploadsService.getTemporaryFile(fileName,
					"buyer_request");
			if (uploadServiceResponse.isPresent()) {
				String fileNameResponse = "";
				try {
					fileNameResponse = URLEncoder.encode(uploadServiceResponse.get().getDisplayName(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					fileNameResponse = uploadServiceResponse.get().getDisplayName();
				}
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition", "attachment; filename =\"" + fileNameResponse + "\"");
				headers.add("FILE_NAME",  fileNameResponse);
				ByteArrayResource byteArrayResource = new ByteArrayResource(uploadServiceResponse.get().getFileBytes());
				
				
				return ResponseEntity.ok().headers(headers)
						.contentType(MediaType.parseMediaType(uploadServiceResponse.get().getFileContentType()+"; charset=UTF-8")).body(byteArrayResource);
			}
			else
				return ResponseEntity.badRequest().build();
		

		

	}
}
