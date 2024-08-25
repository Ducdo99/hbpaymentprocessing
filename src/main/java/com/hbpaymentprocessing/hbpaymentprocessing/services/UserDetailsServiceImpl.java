package com.hbpaymentprocessing.hbpaymentprocessing.services;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.Account;
import com.hbpaymentprocessing.hbpaymentprocessing.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // This class is implements the UserDetailsService interface provided by Spring security.
    // This class authenticates user information when an end user logs in

    @Autowired
    private AccountRepository accountRepository;

    private List<SimpleGrantedAuthority> getAuthorityList(String roleId) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleId.trim());
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account accEntity = accountRepository.findByEmail(email.trim());
        if (accEntity == null) {
            throw new UsernameNotFoundException("Not found this account");
        }
        return new User(accEntity.getEmail().trim(),
                accEntity.getPwd().trim(),
                getAuthorityList(String.valueOf(accEntity.getRole().getId())));
    }
}
