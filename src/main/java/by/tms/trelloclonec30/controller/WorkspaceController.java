package by.tms.trelloclonec30.controller;


import by.tms.trelloclonec30.dto.WorkspaceCreateDto;
import by.tms.trelloclonec30.dto.WorkspaceResponseDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Workspace;
import by.tms.trelloclonec30.repository.AccountRepository;
import by.tms.trelloclonec30.dto.MessageErrorDto;
import by.tms.trelloclonec30.repository.WorkspaceRepository;
import by.tms.trelloclonec30.service.AccountService;
import by.tms.trelloclonec30.service.WorkspaceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private AccountService accountService;


    @PostMapping("/create")
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@RequestBody WorkspaceCreateDto workspaceDto, Authentication authentication) {
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);
        if (account != null) {
            WorkspaceResponseDto workspaceResponseDto = workspaceService.createWorkspace(workspaceDto, account);
            return new ResponseEntity<>(workspaceResponseDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PatchMapping("/edit/{id}")
    public ResponseEntity<WorkspaceResponseDto> editWorkspace(@PathVariable("id") Long id,@RequestBody WorkspaceCreateDto workspaceDto, Authentication authentication){
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);
        Optional<Workspace> workspaceOptional= workspaceRepository.findById(id);
        Workspace workspace = new Workspace();
        if (workspaceOptional.isPresent()) {
          workspace = workspaceOptional.get();
          workspace.setName(workspaceDto.getName());
        }
        else {
            throw new EntityNotFoundException("Workspace not found");
        }
 WorkspaceResponseDto workspaceResponseDto = workspaceService.edit(workspace,account);
        return ResponseEntity.ok(workspaceResponseDto);
     }


/*    @GetMapping("/show")
    public ResponseEntity<?> getWorkspace(Authentication authentication) {
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
           // List<WorkspaceResponseDto> workspaceResponseDtos = workspaceService.getAllWorkspacesByAccount(account);
            if (workspaceResponseDtos.isEmpty()) {
                MessageErrorDto messageError = new MessageErrorDto(HttpStatus.NOT_FOUND.value(), "Workspace not found");
                return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(workspaceResponseDtos, HttpStatus.OK);
        }
    }*/
}
