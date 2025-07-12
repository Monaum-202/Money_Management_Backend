package com.monaum.Money_Management.module.auth;

import com.monaum.Money_Management.module.tokens.Token;
import com.monaum.Money_Management.module.tokens.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class LogoutService implements LogoutHandler {

	@Autowired private TokenRepo tokensRepo;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String jwtToken;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}

		jwtToken = authHeader.substring(7);

		Token storedToken = tokensRepo.findByToken(jwtToken).orElse(null);
		if(storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokensRepo.save(storedToken);
		}

	}

}
