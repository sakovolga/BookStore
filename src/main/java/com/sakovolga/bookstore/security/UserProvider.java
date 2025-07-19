package com.sakovolga.bookstore.security;

import com.sakovolga.bookstore.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * This class provides methods to retrieve the currently authenticated user.
 * @author      Roman Ledenev
 */
@Component
public class UserProvider {
    /**
     * Retrieves the currently authenticated user from the security context.
     *
     * @return the currently authenticated {@link User}
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated.");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Principal is not an instance of CustomUserDetails.");
        }

        return ((CustomUserDetails) principal).getUser();
    }
}
