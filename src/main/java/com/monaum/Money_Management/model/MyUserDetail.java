package com.monaum.Money_Management.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.monaum.Money_Management.security.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class MyUserDetail implements UserDetails {

	private Long id;
	private String email;
	private String password;
	private String roles;
	private List<GrantedAuthority> authorities;

	public MyUserDetail(User user){
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();

		this.roles = "ROLE_USER";
		this.authorities = Arrays.stream(roles.split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.id.toString();
	}

	public Long getUserId() {
		return this.id;
	}

	public String getEmail() {
		return this.email;
	}

}
