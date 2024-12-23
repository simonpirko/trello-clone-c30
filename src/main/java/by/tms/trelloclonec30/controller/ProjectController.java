package by.tms.trelloclonec30.controller;

import by.tms.trelloclonec30.dto.MessageErrorDto;
import by.tms.trelloclonec30.dto.project.ProjectCreateDto;
import by.tms.trelloclonec30.dto.project.ProjectIssuesDto;
import by.tms.trelloclonec30.dto.project.ProjectResponseDto;
import by.tms.trelloclonec30.service.ProjectService;
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

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getIssuesByProjects(@PathVariable("projectId") Long projectId) {
        Optional<ProjectIssuesDto> projectIssuesOpt = projectService.getIssuesByProject(projectId);
        if (projectIssuesOpt.isEmpty()) {
            MessageErrorDto messageError = new MessageErrorDto(HttpStatus.NOT_FOUND.value(), "Project not found");
            return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectIssuesOpt.get(), HttpStatus.OK);
    }
}
