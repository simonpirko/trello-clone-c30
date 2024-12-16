package by.tms.trelloclonec30.controller;


import by.tms.trelloclonec30.dto.WorkspaceCreateDto;
import by.tms.trelloclonec30.dto.WorkspaceResponseDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.repository.AccountRepository;
import by.tms.trelloclonec30.dto.MessageErrorDto;
import by.tms.trelloclonec30.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/create")
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@RequestBody WorkspaceCreateDto workspaceDto, Authentication authentication) {
        String username = authentication.getName();
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent()) {
            WorkspaceResponseDto workspaceResponseDto = workspaceService.createWorkspace(workspaceDto, account.get());
            return new ResponseEntity<>(workspaceResponseDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/show")
    public ResponseEntity<?> getWorkspace(Authentication authentication) {
        String username = authentication.getName();
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<WorkspaceResponseDto> workspaceResponseDtos = workspaceService.getAllWorkspacesByAccount(account.get());
            if (workspaceResponseDtos.isEmpty()) {
                MessageErrorDto messageError = new MessageErrorDto(HttpStatus.NOT_FOUND.value(), "Workspace not found");
                return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(workspaceResponseDtos, HttpStatus.OK);
        }
    }
}
