package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.IssueRequestDto;
import by.tms.trelloclonec30.entity.Issue;
import by.tms.trelloclonec30.repository.IssueRepository;
import org.springframework.stereotype.Service;

//import java.util.Optional;

@Service
public class IssueService /*implements UserDetailsService*/ {
    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public IssueRequestDto create(IssueRequestDto issueRequestDto) {

        Issue issue = new Issue();
        issue.setTitle(issueRequestDto.getTitle());
        issue.setDescription(issueRequestDto.getDescription());

        issueRepository.save(issue);
        return issueRequestDto;
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