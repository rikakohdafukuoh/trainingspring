package com.example.demo.authentication;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        // Userエンティティからusernameとpasswordを取り出す
        String useridFromUser = user.getUsername();
        String passwordFromUser = user.getPassword();
        // ここでは全てのユーザーに対して同じ権限（"ROLE_USER"）を付与しています。
        // 実際のアプリケーションでは、ユーザーの種類によって異なる権限を付与する必要があるかもしれません。
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        CustomUserDetails customUserDetails = new CustomUserDetails(useridFromUser, passwordFromUser, authorities);
        return customUserDetails;
    }
}
