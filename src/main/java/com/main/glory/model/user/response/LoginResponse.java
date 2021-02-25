package com.main.glory.model.user.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	String accessToken;
	String refreshToken;
}
