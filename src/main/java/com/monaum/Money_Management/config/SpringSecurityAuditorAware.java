package com.monaum.Money_Management.config;

import com.monaum.Money_Management.module.user.User;
import com.monaum.Money_Management.module.user.UserRepo;
import com.monaum.Money_Management.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<User> {

    @Autowired
    private UserRepo userRepo;

    public SpringSecurityAuditorAware(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(">> Fetching current auditor");

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            System.out.println(">> No authentication found");
            return Optional.empty();
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            System.out.println(">> Found user ID: " + userDetails.getId());
            return userRepo.findById(userDetails.getId());
        }

        System.out.println(">> Principal is not instance of UserDetailsImpl");
        return Optional.empty();
    }



}
