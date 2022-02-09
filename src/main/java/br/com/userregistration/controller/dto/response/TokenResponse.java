package br.com.userregistration.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private String token;

    private final String type = "Bearer";

}
