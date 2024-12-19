package by.tms.trelloclonec30.controller;

import by.tms.trelloclonec30.dto.MessageErrorDto;
import by.tms.trelloclonec30.dto.issue.IssueCreateDto;
import by.tms.trelloclonec30.dto.issue.IssueDeleteByIssueDto;
import by.tms.trelloclonec30.dto.issue.IssueShowDto;
import by.tms.trelloclonec30.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/issue")
public class IssueController {
    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping()
    public ResponseEntity<IssueCreateDto> create(@RequestBody IssueCreateDto issueCreateDto) {
        var saved = issueService.create(issueCreateDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<?> show(@PathVariable("issueId") Long issueId) {
        Optional<IssueShowDto> showOpt = issueService.show(issueId);  // todo поменять с обработкой Optional
        if (showOpt.isEmpty()) {
            MessageErrorDto messageError = new MessageErrorDto(HttpStatus.NOT_FOUND.value(), "Issue not found");
            return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(showOpt.get(), HttpStatus.OK);
    }
//
//    @DeleteMapping("/by-issue")
//    public ResponseEntity<IssueDeleteByIssueDto> delete(@RequestBody IssueDeleteByIssueDto issueDeleteByIssueDto) {
//        var deleted = issueService.deleteByIssue(issueDeleteByIssueDto);
//        return new ResponseEntity<>(deleted, HttpStatus.OK);
//    }

//  todo
//    @DeleteMapping("/by-project")
//    public ResponseEntity<IssueDeleteByProjectDto> delete(@RequestBody IssueDeleteByProjectDto issueDeleteByProjectDto) {
//        var deleted = issueService.deleteByIssue(issueDeleteByProjectDto);
//        return new ResponseEntity<>(deleted, HttpStatus.ACCEPTED);
//    }
}