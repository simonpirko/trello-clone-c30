package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.ProjectCreateDto;
import by.tms.trelloclonec30.dto.ProjectResponseDto;
import by.tms.trelloclonec30.entity.Project;
import by.tms.trelloclonec30.entity.Workspace;
import by.tms.trelloclonec30.repository.ProjectRepository;
import by.tms.trelloclonec30.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;

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
}
