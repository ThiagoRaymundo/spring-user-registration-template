package br.com.userregistration.common.validations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorResponse {

	private String field;
	private String msg;
}
