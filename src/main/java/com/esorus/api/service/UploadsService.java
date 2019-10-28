package com.esorus.api.service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.esorus.api.domain.Uploads;
import com.esorus.api.domain.User;
import com.esorus.api.repository.UploadsRepository;
import com.esorus.api.repository.UserRepository;
import com.esorus.api.security.SecurityUtils;
import com.esorus.api.service.util.FileInfoVM;

@Service
@Transactional
public class UploadsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UploadsRepository uploadsRepository;

	public Optional<FileInfoVM> saveTemporaryFile(MultipartFile multipartFile, String fileTypeSlug) throws IOException {
		Optional<FileInfoVM> tempraryUploadResponse = Optional.empty();

		User currentUser = userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get())
				.get();
		if (currentUser == null) {
			return tempraryUploadResponse;
		}

		if (!uploadValidation(multipartFile)) {
			return tempraryUploadResponse;
		}

		Uploads originalUpload = new Uploads();
		originalUpload.setContent(multipartFile.getBytes());
		originalUpload.setDataType(multipartFile.getContentType());
		originalUpload.setFileName(multipartFile.getOriginalFilename());
		originalUpload.setFileType(fileTypeSlug);
		originalUpload.setUploadDate(ZonedDateTime.now());
		originalUpload.setUser(currentUser);
		originalUpload.setSavedFileName(ZonedDateTime.now().toInstant().toEpochMilli() + "");
		originalUpload = uploadsRepository.save(originalUpload);

		tempraryUploadResponse = Optional.of(new FileInfoVM(originalUpload.getSavedFileName()));
		return tempraryUploadResponse;

	}

	public Optional<FileInfoVM> getTemporaryFile(String fileName, String fileTypeSlug) {
		Optional<FileInfoVM> tempraryUploadResponse = Optional.empty();
		User currentUser = userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get())
				.get();
		if (currentUser == null) {
			return tempraryUploadResponse;
		}
		Uploads originalPic = uploadsRepository.findOneBySavedFileName(fileName).get();

		if (originalPic == null) {
			return tempraryUploadResponse;
		}
		try {
			byte[] fileBytes = originalPic.getContent();

			FileInfoVM fileInfoVM = new FileInfoVM(fileBytes, originalPic.getSavedFileName(),
					originalPic.getFileName(), originalPic.getDataType(), null);
			tempraryUploadResponse = Optional.of(fileInfoVM);
			return tempraryUploadResponse;

		} catch (Exception e) {
			return tempraryUploadResponse;
		}
	}

	private boolean uploadValidation(MultipartFile multipartFile) {

		if (!(multipartFile.getContentType().equals("image/png") || multipartFile.getContentType().equals("image/jpeg")
				|| multipartFile.getContentType().equals("application/pdf")) || multipartFile.getSize() > 10485760) {

			return false;
		}
		return true;
	}
}
