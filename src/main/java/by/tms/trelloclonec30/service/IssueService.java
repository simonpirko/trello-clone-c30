package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.account.AccountShowDto;
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
        }
//        todo реализовать после мержа в мастер
//        Optional<Project> projectOpt = projectRepository.findById(Long.valueOf(issueCreateDto.getIdProjectr()));
//        if (projectOpt.isPresent()) {
//            issue.setProject(projectOpt.get());
//        } else {
//            throw new EntityNotFoundException("Project not found");            }
//        }
        issueRepository.save(issue);
        return issueCreateDto;
    }

    public Optional<IssueShowDto> show(Long issueId) {
        Optional<Issue> issueOpt = issueRepository.findById(issueId);
        Issue issue;
        IssueShowDto issueShowDto = new IssueShowDto();
        if (issueOpt.isPresent()) {
            issue = issueOpt.get();
        } else {
            return Optional.empty();
        }
        issueShowDto.setId(issue.getId());
        issueShowDto.setTitle(issue.getTitle());
        issueShowDto.setDescription(issue.getDescription());
        AccountShowDto assignee = new AccountShowDto();
        assignee.setId(issue.getAssignee().getId());
        assignee.setUsername(issue.getAssignee().getUsername());
        issueShowDto.setAssignee(assignee);
        AccountShowDto author = new AccountShowDto();
        author.setId(issue.getAuthor().getId());
        author.setUsername(issue.getAuthor().getUsername());
        issueShowDto.setAuthor(author);
        issueShowDto.setProject(issue.getProject());
        issueShowDto.setCurrentStatus(issue.getCurrentStatus());
        return Optional.of(issueShowDto);
    }

    public IssueDeleteByIssueDto deleteByIssue(IssueDeleteByIssueDto issueDeleteByIssueDto) {
        issueRepository.deleteById(Long.valueOf(issueDeleteByIssueDto.getId()));
        return issueDeleteByIssueDto;
    }
// todo реализовать после мержа в мастер
//    public IssueDeleteByProjectDto deleteByIssue(IssueDeleteByProjectDto issueDeleteByProjectDto) {
//        issueRepository.deleteById(Long.valueOf(issueDeleteByProjectDto.getId()));
//        return IssueDeleteByProjectDto;
//    }
}