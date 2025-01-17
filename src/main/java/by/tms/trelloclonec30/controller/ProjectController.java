package by.tms.trelloclonec30.controller;

import by.tms.trelloclonec30.dto.MessageErrorDto;
import by.tms.trelloclonec30.dto.project.ProjectCreateDto;
import by.tms.trelloclonec30.dto.project.ProjectIssuesDto;
import by.tms.trelloclonec30.dto.project.ProjectResponseDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.service.AccountService;
import by.tms.trelloclonec30.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody ProjectCreateDto projectCreateDto,
                                                            Authentication authentication) {
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);
        return new ResponseEntity<>(projectService.createProject(projectCreateDto, account), HttpStatus.CREATED);
    }

    @GetMapping("/show/{workspaceId}")
    public ResponseEntity<?> getAllProjects(@PathVariable("workspaceId") Long workspaceId) {
        List<ProjectResponseDto> projects = projectService.getAllProjectsByWorkspace(workspaceId);
        if (projects.isEmpty()) {
            MessageErrorDto messageError = new MessageErrorDto(HttpStatus.NOT_FOUND.value(), "Project not found");
            return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
  
   @GetMapping("/{projectId}")
   public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable("projectId") Long projectId, Authentication authentication) {
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);
        ProjectResponseDto projectResponseDto = projectService.findById(projectId);
        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") Long projectId, Authentication authentication) {
        String username = authentication.getName();
        Account account = accountService.checkAccount(username);
        projectService.deleteProject(projectId, account);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{projectId}/issues")
    public ResponseEntity<?> getIssuesByProjects(@PathVariable("projectId") Long projectId, Authentication authentication) {

        String username = authentication.getName();
        Account account = accountService.checkAccount(username);

        Optional<ProjectIssuesDto> projectIssuesOpt = projectService.getIssuesByProject(projectId,account);
        if (projectIssuesOpt.isEmpty()) {
            MessageErrorDto messageError = new MessageErrorDto(HttpStatus.NOT_FOUND.value(), "Project not found");
            return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectIssuesOpt.get(), HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteTeamInProjects(@RequestBody InviteTeamDTO inviteTeamDTO) {

        if(projectService.inviteTeam(inviteTeamDTO.getIdProject(), inviteTeamDTO.getIdTeam())){
            return new ResponseEntity<>(inviteTeamDTO,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(inviteTeamDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
