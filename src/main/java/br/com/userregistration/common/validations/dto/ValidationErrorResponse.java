package br.com.userregistration.common.validations.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationErrorResponse extends StandardErrorResponse {

	private List<FieldErrorResponse> error = new ArrayList<>();

	public ValidationErrorResponse(Integer status, String msg) {		
		addStandrError(status, msg);		
	}

	public void addError(String field, String msg) {
		this.error.add(new FieldErrorResponse(field, msg));
	}

}
