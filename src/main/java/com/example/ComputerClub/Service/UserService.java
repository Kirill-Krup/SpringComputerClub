package com.example.ComputerClub.Service;

import com.example.ComputerClub.DBO.UserRepository;
import com.example.ComputerClub.Model.Roles;
import com.example.ComputerClub.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository authorizationDbo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository authorizationDbo, PasswordEncoder passwordEncoder) {
        this.authorizationDbo = authorizationDbo;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(User user) {
        if(authorizationDbo.findByLogin(user.getLogin()).isPresent()) {
            throw new UsernameNotFoundException("Логин уже занят");
        }
        if(authorizationDbo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email уже занят");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(new Roles(2,"USER"));
        user.setWallet(0.00);
        return authorizationDbo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        Optional<User> userOptional;
        if(str.contains("@")){
            userOptional = authorizationDbo.findByEmail(str);
        }else{
            userOptional = authorizationDbo.findByLogin(str);
        }
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Такой пользователь не найден, введите правильность введённых данных"));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRoleName()))
        );
    }

    public List<User> getUsers() {return authorizationDbo.findAll();}

}
