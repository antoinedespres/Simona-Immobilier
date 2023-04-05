package com.simona.auth.config;

import com.simona.auth.model.Account;
import com.simona.auth.web.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(email);
        // Map our 'Account' model to Spring's 'UserDetails' interface
        // because 'loadUserByUsername()' must return UserDetails
        return account.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with email :" + email));
    }
}
