package com.example.franconews.services;

import com.example.franconews.entities.User;
import com.example.franconews.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow();

        if (user != null) {
            List<GrantedAuthority> permissions = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());

            permissions.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("user", user);

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), permissions);
        }
        return null;
    }
}
