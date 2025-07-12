package com.monaum.Money_Management.module.user;

import java.util.Optional;

import com.monaum.Money_Management.model.MyUserDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService  {

	@Autowired private UserRepo usersRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("Email required");
		}

		Optional<User> userOp = usersRepo.findById(Long.valueOf(username));
		if(!userOp.isPresent()) throw new UsernameNotFoundException("User not exist.");

		User user = userOp.get();
		if(Boolean.FALSE.equals(user.getIsActive())) {
			throw new UsernameNotFoundException("User inactive.");
		}


		return new MyUserDetail(user);
	}

}
