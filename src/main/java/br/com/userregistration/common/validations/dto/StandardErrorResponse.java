package br.com.userregistration.common.validations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StandardErrorResponse {

	private Integer status;
	private String msg;
	private final Long timeStamp = System.currentTimeMillis() / 1000;

	
	public void addStandrError(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
	}

}
