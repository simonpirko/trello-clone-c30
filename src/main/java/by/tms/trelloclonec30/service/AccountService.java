package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(Account account) {
        account.setPassword(new BCryptPasswordEncoder(11).encode(account.getPassword()));
        accountRepository.save(account);
        return account;
    }

    public Account checkAccount(String username) {
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent()) {
            var acc = account.get();
            return User
                    .withUsername(acc.getUsername())
                    .password(acc.getPassword())
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException(username);
    }
}
