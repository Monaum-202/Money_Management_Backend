package com.monaum.Money_Management.model;

import com.monaum.Money_Management.module.user.User;
import com.monaum.Money_Management.module.user.UserRepo;
import com.monaum.Money_Management.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public abstract class BaseService {
    @Autowired
    protected UserRepo userRepository;

    protected Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }

    protected User getAuthenticatedUser() {
        return userRepository.findById(this.getAuthenticatedUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID"));
    }


}