package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.entity.Issue;
import by.tms.trelloclonec30.repository.IssueRepository;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import java.util.Optional;

@Service
public class IssueService /*implements UserDetailsService*/ {
    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue create(Issue issue) {
        issueRepository.save(issue);
        return issue;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Account> account = accountRepository.findByUsername(username);
//        if (account.isPresent()) {
//            var acc = account.get();
//            return User
//                    .withUsername(acc.getUsername())
//                    .password(acc.getPassword())
//                    .roles("USER")
//                    .build();
//        }
//        throw new UsernameNotFoundException(username);
//    }
}