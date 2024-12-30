package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.account.AccountShowDto;
import by.tms.trelloclonec30.dto.issue.IssueByProjectDto;
import by.tms.trelloclonec30.dto.issue.IssueCreateDto;
import by.tms.trelloclonec30.dto.issue.IssueShowDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Issue;
import by.tms.trelloclonec30.entity.Project;
import by.tms.trelloclonec30.repository.AccountRepository;
import by.tms.trelloclonec30.repository.IssueRepository;
import by.tms.trelloclonec30.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository,
                        AccountRepository accountRepository,
                        ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.accountRepository = accountRepository;
        this.projectRepository = projectRepository;
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
        Optional<Project> projectOpt = projectRepository.findById(Long.valueOf(issueCreateDto.getIdProject()));
        if (projectOpt.isPresent()) {
            issue.setProject(projectOpt.get());
        } else {
            throw new EntityNotFoundException("Project not found");
        }
        issueRepository.save(issue);
        return issueCreateDto;
    }

    public Optional<IssueShowDto> show(Long issueId) {
        Optional<Issue> issueOpt = issueRepository.findById(issueId);
        Issue issue;
//        IssueShowDto issueShowDto = new IssueShowDto();
        if (issueOpt.isPresent()) {
            issue = issueOpt.get();
        } else {
            return Optional.empty();
        }
//        issueShowDto = getIssueShowDto(issue);
//        issueShowDto.setId(issue.getId());
//        issueShowDto.setTitle(issue.getTitle());
//        issueShowDto.setDescription(issue.getDescription());
//        AccountShowDto assignee = new AccountShowDto();
//        assignee.setId(issue.getAssignee().getId());
//        assignee.setUsername(issue.getAssignee().getUsername());
//        issueShowDto.setAssignee(assignee);
//        AccountShowDto author = new AccountShowDto();
//        author.setId(issue.getAuthor().getId());
//        author.setUsername(issue.getAuthor().getUsername());
//      issueShowDto.setAuthor(author);
//        issueShowDto.setProject(issue.getProject());
//        issueShowDto.setCurrentStatus(issue.getCurrentStatus());
        return Optional.of(getIssueShowDto(issue));
    }

    public List<IssueByProjectDto> issuesByProject(Long projectId) {
        List<Issue> issues = issueRepository.findByProjectId(projectId);
        List<IssueByProjectDto> issuesByProjectDto = new ArrayList<>();
        for (Issue issue : issues) issuesByProjectDto.add(getIssueByProjectDto(issue));
        return issuesByProjectDto;
    }

    public void deleteById(Long issueId) {
        issueRepository.deleteById(issueId);
    }

    private IssueShowDto getIssueShowDto(Issue issue) {
        IssueShowDto issueShowDto = new IssueShowDto();
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
        return issueShowDto;
    }

    private IssueByProjectDto getIssueByProjectDto(Issue issue) {
        IssueByProjectDto issueByProjectDto = new IssueByProjectDto();
        issueByProjectDto.setId(issue.getId());
        issueByProjectDto.setTitle(issue.getTitle());
        issueByProjectDto.setDescription(issue.getDescription());
        AccountShowDto assignee = new AccountShowDto();
        assignee.setId(issue.getAssignee().getId());
        assignee.setUsername(issue.getAssignee().getUsername());
        issueByProjectDto.setAssignee(assignee);
        AccountShowDto author = new AccountShowDto();
        author.setId(issue.getAuthor().getId());
        author.setUsername(issue.getAuthor().getUsername());
        issueByProjectDto.setAuthor(author);
        issueByProjectDto.setCurrentStatus(issue.getCurrentStatus());
        return issueByProjectDto;
    }
}