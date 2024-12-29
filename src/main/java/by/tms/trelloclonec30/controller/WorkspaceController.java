package by.tms.trelloclonec30.controller;


import by.tms.trelloclonec30.dto.MessageErrorDto;
import by.tms.trelloclonec30.dto.workspace.WorkspaceCreateDto;
import by.tms.trelloclonec30.dto.workspace.WorkspaceResponseDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Workspace;
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


@RestController
@RequestMapping("/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private AccountService accountService;


    @PostMapping
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@RequestBody WorkspaceCreateDto workspaceDto, Authentication authentication) {
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);
        WorkspaceResponseDto workspaceResponseDto = workspaceService.createWorkspace(workspaceDto, account);
        return new ResponseEntity<>(workspaceResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WorkspaceResponseDto> editWorkspace(@PathVariable("id") Long id,
                                                              @RequestBody WorkspaceCreateDto workspaceDto,
                                                              Authentication authentication){
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);
        Optional<Workspace> workspaceOptional= workspaceRepository.findById(id);
        Workspace workspace;
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


    @GetMapping
    public ResponseEntity<?> getWorkspace(Authentication authentication) {
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);

        List<WorkspaceResponseDto> workspaceResponseDtos = workspaceService.getAllWorkspacesByAccount(account);
        if (workspaceResponseDtos.isEmpty()) {
            MessageErrorDto messageError = new MessageErrorDto(HttpStatus.NOT_FOUND.value(), "Workspace not found");
            return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workspaceResponseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{worspaceId}")
    public ResponseEntity<?> deleteWorkspace(@PathVariable("worspaceId") Long workspaceId, Authentication authentication) {
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);
        workspaceService.deleteWorkspaceById(workspaceId, account);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
