package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.IssueCreateDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Issue;
import by.tms.trelloclonec30.entity.Project;
import by.tms.trelloclonec30.repository.IssueRepository;
import org.springframework.stereotype.Service;

@Service
public class IssueService {
    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public IssueCreateDto create(IssueCreateDto issueCreateDto) {
        Account author = new Account();
        Account assignee = new Account();
        Project project = new Project();
        Issue issue = new Issue();

        issue.setTitle(issueCreateDto.getTitle());
        issue.setDescription(issueCreateDto.getDescription());
        issue.setCurrentStatus(issueCreateDto.getCurrentStatus());

//        issue.setAuthor().setId(issueCreateDto.getIdAssignee());
//        issue.getAuthor(issueCreateDto.getIdAuthor());
//        issue.getProject(issueCreateDto.getIdProject());

        issueRepository.save(issue);
        return issueCreateDto;
    }
}