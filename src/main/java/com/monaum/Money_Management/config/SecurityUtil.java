package com.monaum.Money_Management.config;

import com.monaum.Money_Management.exception.CustomException;
import com.monaum.Money_Management.module.user.User;
import com.monaum.Money_Management.module.user.UserRepo;
import com.monaum.Money_Management.security.UserDetailsImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepo userRepo;
    private static UserRepo staticUserRepo;

    @PostConstruct
    public void init() {
        staticUserRepo = userRepo;
    }

    public Optional<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return Optional.empty();
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return Optional.of(staticUserRepo.getReferenceById(userDetails.getId()));
        }

        return Optional.empty();
    }

    protected User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            throw new UsernameNotFoundException("User not authenticated");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Retrieve the user by their username (from the token)
        return userRepo.findByUserNameIgnoreCase(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()));
    }


}
