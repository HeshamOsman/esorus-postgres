package com.esorus.api.service;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import com.esorus.api.web.rest.errors.ErrorConstants;

public class InsufficientCompanyData extends AbstractThrowableProblem {

	public InsufficientCompanyData() {
		super(ErrorConstants.EMAIL_NOT_FOUND_TYPE, "Company data is insufficient", Status.BAD_REQUEST);
	}
}
