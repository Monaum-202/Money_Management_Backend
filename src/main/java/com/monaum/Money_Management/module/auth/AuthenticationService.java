package com.monaum.Money_Management.module.auth;

import java.io.IOException;
import java.util.List;

import com.monaum.Money_Management.enums.TokenType;
import com.monaum.Money_Management.exception.CustomException;
import com.monaum.Money_Management.model.MyUserDetail;
import com.monaum.Money_Management.security.JwtService;
import com.monaum.Money_Management.module.tokens.Token;
import com.monaum.Money_Management.module.tokens.TokenRepo;
import com.monaum.Money_Management.module.user.User;
import com.monaum.Money_Management.module.user.UserRepo;
import com.monaum.Money_Management.module.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

/**
 * Zubayer Ahamed
 * 
 * @since Jun 22, 2025
 */
@Service
public class AuthenticationService {


	@Autowired private UserRepo userRepo;
	@Autowired private JwtService jwtService;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired private TokenRepo tokensRepo;
	@Autowired private UserService userService;

	@Transactional
	public AuthenticationResDto register(RegisterRequestDto request) {
		// 1. Check if user already exists
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new CustomException("Email is already registered.", HttpStatus.BAD_REQUEST);
		}

		// 2. Create user
		User user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.isActive(Boolean.TRUE).build();

		user = userRepo.save(user);

//		// TODO: Create User preferences with system default values
//		UserPreference up = UserPreference.builder()
//				.userId(user.getId())
//				.language(Language.ENGLISH.name())
//				.homeView("TODAY")
//				.timeZone("Asia/Dhaka")
//				.timeFormat(TimeFormat.HOUR_12.name())
//				.dateFormat(DateFormat.DD_MMM_YYYY.name())
//				.weekStart(Days.SUN.name())
//				.nextWeek(Days.SUN.name())
//				.weekend(Days.FRI.name())
//				.enabledBrowserNoti(false)
//				.enabledEmailNoti(false)
//				.enabledPushNoti(false)
//				.enabledSmsNoti(false)
//				.build();
//
//		userPreferenceRepo.save(up);
//
//		// 3. Create workspace (business)
//		Workspace workspace = Workspace.builder()
//				.name(user.getFirstName() + "'s Workspace")
//				.isSystemDefined(Boolean.TRUE)
//				.isActive(Boolean.TRUE)
//				.build();
//
//		workspace = workspaceRepo.save(workspace);
//
//		// 4. Create user-business relationship
//		UserWorkspace userWorkspace = UserWorkspace.builder()
//				.userId(user.getId())
//				.workspaceId(workspace.getId())
//				.isPrimary(Boolean.TRUE)
//				.isAdmin(Boolean.TRUE)
//				.isCollaborator(Boolean.FALSE).build();
//
//		userWorkspace = userWorkspacesRepo.save(userWorkspace);
//
//		// 5. Create default project (Index)
//		Project project = Project.builder()
//				.workspaceId(workspace.getId())
//				.name("Inbox")
//				.color("#000000")
//				.seqn(-999)
//				.layoutType(LayoutType.LIST)
//				.isSystemDefined(Boolean.TRUE)
//				.isFavourite(Boolean.FALSE)
//				.isInheritSettings(Boolean.FALSE)
//				.build();
//
//		project  = projectRepo.save(project);
//
//		// 6. Create a default status (Completed)
//		Workflow workflow = Workflow.builder()
//				.referenceId(workspace.getId())
//				.name("Completed")
//				.isSystemDefined(Boolean.TRUE)
//				.seqn(999)
//				.color("#000000")
//				.build();
//
//		workflow = workflowRepo.save(workflow);

		// 7. Generate JWT token and Refresh token
		var jwtToken = jwtService.generateToken(new MyUserDetail(user));
		var refreshToken = jwtService.generateRefreshToken(new MyUserDetail(user));

		// 8. Save User Token
		saveUserToken(user.getId(), jwtToken);

		// 9. Return token
		return AuthenticationResDto.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	@Transactional
	public AuthenticationResDto authenticate(AuthenticationReqDto request) {
		// 1. Find user by email
		User xusers = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Email is not registered."));

		// 2. Check if user is active
		if (Boolean.FALSE.equals(xusers.getIsActive())) {
			throw new RuntimeException("User account is inactive.");
		}

		// 3. Verify password
		if (!passwordEncoder.matches(request.getPassword(), xusers.getPassword())) {
			throw new RuntimeException("Invalid credentials.");
		}


		// 7. Generate token
		var jwtToken = jwtService.generateToken(new MyUserDetail(xusers));
		var refreshToken = jwtService.generateRefreshToken(new MyUserDetail(xusers));

		// 8. Revoke tokens and save new token
		revokeAllUserTokens(xusers.getId());
		saveUserToken(xusers.getId(), jwtToken);

		// 9. Return token
		return AuthenticationResDto.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	@Transactional
	void revokeAllUserTokens(Long zuser) {
		List<Token> validTokens = tokensRepo.findAllByUserIdAndRevokedAndExpired(zuser, false, false);
		if (validTokens.isEmpty())
			return;

		validTokens.forEach(t -> {
			t.setRevoked(true);
			t.setExpired(true);
		});

		tokensRepo.saveAll(validTokens);
	}

	@Transactional
	void saveUserToken(Long zuser, String jwtToken) {
		Token xtoken = Token.builder()
				.userId(zuser)
				.token(jwtToken)
				.revoked(false)
				.expired(false)
				.xtype(TokenType.BEARER)
				.build();

		tokensRepo.save(xtoken);
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userId;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userId = jwtService.extractUsername(refreshToken);
		if (StringUtils.isNotBlank(userId)) {
			MyUserDetail userDetails = (MyUserDetail) userService.loadUserByUsername(userId);

			if (jwtService.isTokenValid(refreshToken, userDetails)) {
				var accessToken = jwtService.generateToken(userDetails);
				revokeAllUserTokens(Long.valueOf(userDetails.getUsername()));
				saveUserToken(Long.valueOf(userDetails.getUsername()), accessToken);
				var authResponse = AuthenticationResDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

}
