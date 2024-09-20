package com.sakovolga.bookstore.security;

import com.sakovolga.bookstore.entity.User;
import com.sakovolga.bookstore.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Находим пользователя в базе данных по email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Создаем и возвращаем CustomUserDetails с email и ролями пользователя
        return new CustomUserDetails(user.getEmail(), user.getRoles().stream().toList());
    }
}
