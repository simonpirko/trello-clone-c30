package by.tms.trelloclonec30.controller;

import by.tms.trelloclonec30.dto.issue.IssueCreateDto;
import by.tms.trelloclonec30.dto.issue.IssueDeleteByIssueDto;
import by.tms.trelloclonec30.dto.issue.IssueShowDto;
import by.tms.trelloclonec30.entity.Issue;
import by.tms.trelloclonec30.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issue")
public class IssueController {
    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/create")
    public ResponseEntity<IssueCreateDto> create(@RequestBody IssueCreateDto issueCreateDto) {
        var saved = issueService.create(issueCreateDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/show")
    public ResponseEntity<Issue> create(@RequestBody IssueShowDto issueShowDto) {
        var show = issueService.show(issueShowDto);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @DeleteMapping("/by-issue")
    public ResponseEntity<IssueDeleteByIssueDto> delete(@RequestBody IssueDeleteByIssueDto issueDeleteByIssueDto) {
        var deleted = issueService.deleteByIssue(issueDeleteByIssueDto);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

//  todo
//    @DeleteMapping("/by-project")
//    public ResponseEntity<IssueDeleteByProjectDto> delete(@RequestBody IssueDeleteByProjectDto issueDeleteByProjectDto) {
//        var deleted = issueService.deleteByIssue(issueDeleteByProjectDto);
//        return new ResponseEntity<>(deleted, HttpStatus.ACCEPTED);
//    }
}