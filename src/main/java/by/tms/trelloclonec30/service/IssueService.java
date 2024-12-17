package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.issue.IssueCreateDto;
import by.tms.trelloclonec30.dto.issue.IssueDeleteByIssueDto;
import by.tms.trelloclonec30.dto.issue.IssueShowDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Issue;
import by.tms.trelloclonec30.repository.AccountRepository;
import by.tms.trelloclonec30.repository.IssueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository,
                        AccountRepository accountRepository) {
        this.issueRepository = issueRepository;
        this.accountRepository = accountRepository;
    }

    public IssueCreateDto create(IssueCreateDto issueCreateDto) {
        Issue issue = new Issue();
        issue.setTitle(issueCreateDto.getTitle());
        issue.setDescription(issueCreateDto.getDescription());
        issue.setCurrentStatus(issueCreateDto.getCurrentStatus());
        Optional<Account> authorOpt = accountRepository.findById(Long.valueOf(issueCreateDto.getIdAuthor()));
        if (authorOpt.isPresent()) {
            issue.setAuthor(authorOpt.get());
        } else {
            throw new EntityNotFoundException("Author not found");
        }
        Optional<Account> assigneeOpt = accountRepository.findById(Long.valueOf(issueCreateDto.getIdAssignee()));
        if (assigneeOpt.isPresent()) {
            issue.setAssignee(assigneeOpt.get());
        } else {
            throw new EntityNotFoundException("Assignee not found");
        }

//        issue.setProject();

        issueRepository.save(issue);
        return issueCreateDto;
    }



    public Issue show(IssueShowDto issueShowDto) {
        Issue issue = new Issue();
        Optional<Issue> issueOpt = issueRepository.findById(Long.valueOf(issueShowDto.getId()));
        if (issueOpt.isPresent()) {
            return issueOpt.get();
        } else {
            throw new EntityNotFoundException("Issue not found");
        }
    }

    public IssueDeleteByIssueDto deleteByIssue(IssueDeleteByIssueDto issueDeleteByIssueDto) {
        issueRepository.deleteById(Long.valueOf(issueDeleteByIssueDto.getId()));
        return issueDeleteByIssueDto;
    }
// todo
//    public IssueDeleteByProjectDto deleteByIssue(IssueDeleteByProjectDto issueDeleteByProjectDto) {
//        issueRepository.deleteById(Long.valueOf(issueDeleteByProjectDto.getId()));
//        return IssueDeleteByProjectDto;
//    }
}