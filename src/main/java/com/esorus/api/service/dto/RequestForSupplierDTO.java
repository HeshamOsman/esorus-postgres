package com.esorus.api.service.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;


public class RequestForSupplierDTO {
	@NotBlank
	private String professionalRole;
	@NotBlank
	private String typeOfWorkNeeded;
	@NotBlank
	private String projectType;
	@NotBlank
	private String projectPhase;

	private String uploadedPic;

	private String uploadedBOQ;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate deliveryDate;

	private String detailes;

	private String quantity;
    @NotNull
	private Boolean boq;
    
    private String special;

	private String phoneNumber;

	public String getTypeOfWorkNeeded() {
		return typeOfWorkNeeded;
	}

	public void setTypeOfWorkNeeded(String typeOfWorkNeeded) {
		this.typeOfWorkNeeded = typeOfWorkNeeded;
	}

	
	
	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getProfessionalRole() {
		return professionalRole;
	}

	public void setProfessionalRole(String professionalRole) {
		this.professionalRole = professionalRole;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectPhase() {
		return projectPhase;
	}

	public void setProjectPhase(String projectPhase) {
		this.projectPhase = projectPhase;
	}

	public String getUploadedPic() {
		return uploadedPic;
	}

	public void setUploadedPic(String uploadedPic) {
		this.uploadedPic = uploadedPic;
	}

	public String getUploadedBOQ() {
		return uploadedBOQ;
	}

	public void setUploadedBOQ(String uploadedBOQ) {
		this.uploadedBOQ = uploadedBOQ;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDetailes() {
		return detailes;
	}

	public void setDetailes(String detailes) {
		this.detailes = detailes;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Boolean getBoq() {
		return boq;
	}

	public void setBoq(Boolean boq) {
		this.boq = boq;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
