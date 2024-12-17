package by.tms.trelloclonec30.controller;

import by.tms.trelloclonec30.dto.IssueCreateDto;
import by.tms.trelloclonec30.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/delete")
//    public ResponseEntity<IssueCreateDto> delete() {
////        var saved = issueService.create(issueCreateDto);
//        return new ResponseEntity<>(saved, HttpStatus.CREATED);
//    }
}