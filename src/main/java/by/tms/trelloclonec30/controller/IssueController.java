package by.tms.trelloclonec30.controller;

import by.tms.trelloclonec30.entity.Issue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/issue")
public class IssueController {

//    private final AccountService accountService;

//    public AccountController(AccountService accountService) {
//        this.accountService = accountService;
//    }

    @PostMapping
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue) {
//        var saved = accountService.create(account);
//        return new ResponseEntity<>(saved, HttpStatus.CREATED);
        return null;
    }
}