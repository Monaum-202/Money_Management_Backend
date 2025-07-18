package com.monaum.Money_Management.module.auth;

import com.monaum.Money_Management.annotations.RestApiController;
import com.monaum.Money_Management.enums.ResponseStatusType;
import com.monaum.Money_Management.model.ResponseBuilder;
import com.monaum.Money_Management.model.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@RestApiController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private @Autowired AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<SuccessResponse<AuthenticationResDto>> register(@RequestBody RegisterRequestDto request) {
		AuthenticationResDto data = authenticationService.register(request);
		return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, data);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<SuccessResponse<AuthenticationResDto>> authenticate(@RequestBody AuthenticationReqDto request) {
		AuthenticationResDto data = authenticationService.authenticate(request);
		return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, data);
	}

	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		authenticationService.refreshToken(request, response);
	}
}
