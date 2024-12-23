package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.TeamDto;
import by.tms.trelloclonec30.dto.issue.IssueByProjectDto;
import by.tms.trelloclonec30.dto.project.ProjectCreateDto;
import by.tms.trelloclonec30.dto.project.ProjectIssuesDto;
import by.tms.trelloclonec30.dto.project.ProjectResponseDto;
import by.tms.trelloclonec30.entity.Project;
import by.tms.trelloclonec30.entity.Team;
import by.tms.trelloclonec30.entity.Workspace;
import by.tms.trelloclonec30.repository.ProjectRepository;
import by.tms.trelloclonec30.repository.WorkspaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final WorkspaceRepository workspaceRepository;
    private final IssueService issueService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          WorkspaceRepository workspaceRepository,
                          IssueService issueService) {
        this.projectRepository = projectRepository;
        this.workspaceRepository = workspaceRepository;
        this.issueService = issueService;
    }

    public List<ProjectResponseDto> getAllProjectsByWorkspace(Long workspaceId) {
        List<Project> projects = projectRepository.findAllByWorkspaceId(workspaceId);
        List<ProjectResponseDto> projectResponseDtos = new ArrayList<>();
        for (Project project : projects) {
            ProjectResponseDto projectResponseDto = new ProjectResponseDto();
            projectResponseDto.setProjectId(project.getId());
            projectResponseDto.setProjectName(project.getName());
            projectResponseDto.setProjectDescription(project.getDescription());
            projectResponseDto.setWorkspaceId(workspaceId);
            projectResponseDtos.add(projectResponseDto);
        }
        return projectResponseDtos;
    }

    public ProjectResponseDto createProject(ProjectCreateDto projectCreateDto) {
        Project project = new Project();
        project.setName(projectCreateDto.getName());
        project.setDescription(projectCreateDto.getDescription());
        Optional<Workspace> workspace = workspaceRepository.findById(projectCreateDto.getId_workspace());
        workspace.ifPresent(project::setWorkspace);
        projectRepository.save(project);
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setProjectId(project.getId());
        projectResponseDto.setProjectName(project.getName());
        projectResponseDto.setProjectDescription(project.getDescription());
        projectResponseDto.setWorkspaceId(projectCreateDto.getId_workspace());
        return projectResponseDto;
    }

    public ProjectResponseDto findById(Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            ProjectResponseDto projectResponseDto = new ProjectResponseDto();
            projectResponseDto.setProjectId(projectId);
            projectResponseDto.setProjectName(project.getName());
            projectResponseDto.setProjectDescription(project.getDescription());
            projectResponseDto.setWorkspaceId(project.getWorkspace().getId());
            List<TeamDto> teamDtos = new ArrayList<>();
            for (Team team : project.getTeams()) {
               teamDtos.add(teamService.convertTeamToTeamDto(team));
            }
            projectResponseDto.setTeams(teamDtos);
            return projectResponseDto;
        }
        else {
            throw new EntityNotFoundException("Project not found");
        }
    }

    public Optional<ProjectIssuesDto> getIssuesByProject(Long projectId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        Project project;
        if (projectOpt.isPresent()) {
            project = projectOpt.get();
        } else {
            return Optional.empty();
        }
        ProjectIssuesDto projectIssuesDto = new ProjectIssuesDto();
        projectIssuesDto.setId(project.getId());
        projectIssuesDto.setName(project.getName());
        projectIssuesDto.setDescription(project.getDescription());
        projectIssuesDto.setIssues(issueService.issuesByProject(project.getId()));
        return Optional.of(projectIssuesDto);
    }
}
