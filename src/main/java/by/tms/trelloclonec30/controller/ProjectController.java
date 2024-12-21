package by.tms.trelloclonec30.controller;

import by.tms.trelloclonec30.dto.MessageErrorDto;
import by.tms.trelloclonec30.dto.ProjectCreateDto;
import by.tms.trelloclonec30.dto.ProjectResponseDto;
import by.tms.trelloclonec30.dto.WorkspaceResponseDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Project;
import by.tms.trelloclonec30.repository.ProjectRepository;
import by.tms.trelloclonec30.service.ProjectService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody ProjectCreateDto projectCreateDto) {
        return new ResponseEntity<>(projectService.createProject(projectCreateDto), HttpStatus.CREATED);
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
    @GetMapping("/find-by-id/{projectId}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable("projectId") Long projectId, Authentication authentication) {
        ProjectResponseDto projectResponseDto = projectService.findById(projectId);
        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);
    }
}
