package com.esorus.api.service;

public class InsufficientCompanyData extends RuntimeException {

	public InsufficientCompanyData() {
		super("Company data is insufficient");
	}
}
