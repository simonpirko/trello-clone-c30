package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.TeamDto;
import by.tms.trelloclonec30.dto.issue.IssueByProjectDto;
import by.tms.trelloclonec30.dto.project.InviteTeamDTO;
import by.tms.trelloclonec30.dto.project.ProjectCreateDto;
import by.tms.trelloclonec30.dto.project.ProjectIssuesDto;
import by.tms.trelloclonec30.dto.project.ProjectResponseDto;
import by.tms.trelloclonec30.entity.*;
import by.tms.trelloclonec30.repository.ProjectRepository;
import by.tms.trelloclonec30.repository.TeamRepository;
import by.tms.trelloclonec30.repository.WorkspaceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final WorkspaceRepository workspaceRepository;
    private final IssueService issueService;
    private final TeamService teamService;
    private final WorkspaceService workspaceService;
    
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          WorkspaceRepository workspaceRepository,
                          IssueService issueService, TeamService teamService, WorkspaceService workspaceService) {
        this.projectRepository = projectRepository;
        this.workspaceRepository = workspaceRepository;
        this.issueService = issueService;
        this.teamService = teamService;
        this.workspaceService = workspaceService;
    }

    @Autowired
    private RolesRepository rolesRepository;

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

    @Transactional
    public ProjectResponseDto createProject(ProjectCreateDto projectCreateDto, Account account) {
        Optional<Workspace> workspaceOptional = workspaceRepository.findById(projectCreateDto.getId_workspace());
        if (workspaceOptional.isPresent()) {
            Workspace workspace = workspaceOptional.get();
            if (workspaceService.checkRoles(workspace, account)) {
                Project project = new Project();
                project.setName(projectCreateDto.getName());
                project.setDescription(projectCreateDto.getDescription());
                project.setWorkspace(workspace);
                projectRepository.save(project);
                ProjectResponseDto projectResponseDto = new ProjectResponseDto();
                projectResponseDto.setProjectId(project.getId());
                projectResponseDto.setProjectName(project.getName());
                projectResponseDto.setProjectDescription(project.getDescription());
                projectResponseDto.setWorkspaceId(projectCreateDto.getId_workspace());
                return projectResponseDto;
            } else {
                throw new IllegalAccessError("Not allowed to create project");
            }
        } else {
            throw new EntityNotFoundException("Workspace not found");
        }
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
        } else {
            throw new EntityNotFoundException("Project not found");
        }
    }

    public Optional<ProjectIssuesDto> getIssuesByProject(Long projectId,Account account) {
        if(checkRoles(projectId, account)){

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

        }else{
            throw new IllegalAccessError("Not allowed to show issues");
        }
    }

    private boolean checkRoles(Long projectId, Account account) {
        Project project = projectRepository.findById(projectId).get();
        Set<Roles> rolesSet = project.getRoles();
        Roles roles = rolesSet.iterator().next();
        if (roles.getAccount().equals(account)) {
            return true;
        }
        return false;
    }

    public Boolean inviteTeam(Long projectId, Long teamId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        Optional<Team> teamOpt = teamRepository.findById(teamId);
       if (projectOpt.isPresent() && teamOpt.isPresent()) {
          Project project = projectOpt.get();
          Team team = teamOpt.get();
          if(project.getTeams().contains(team)) {
              throw new EntityNotFoundException("Team is already in this Project");
          }
          project.getTeams().add(team);
          projectRepository.save(project);
          return true;
       }else {
           throw new EntityNotFoundException("Team or Project not found");
       }
    }

    public void deleteProject(Long projectId, Account account) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            if (checkRoles(project, account)) {
                projectRepository.delete(project);
            } else {
                throw new IllegalAccessError("Not allowed to delete project");
            }
        } else {
            throw new EntityNotFoundException("Project with id " + projectId + " not found");
        }
    }

    public boolean checkRoles(Project project, Account account) {
        Optional<Workspace> workspaceOptional = workspaceRepository.findById(project.getWorkspace().getId());
        if (workspaceOptional.isPresent()) {
            Workspace workspace = workspaceOptional.get();
            if (workspaceService.checkRoles(workspace, account)) {
                return true;
            } else {
                Set<Roles> rolesSet = project.getRoles();
                for (Roles role : rolesSet) {
                    if ((role.getRole().equals(Role.PROJECT_LEADER)) && (role.getAccount().equals(account))) {
                        return true;
                    }
                }
            }
        } else {
            throw new EntityNotFoundException("Workspace not found");
        }
        return false;
    }
}
