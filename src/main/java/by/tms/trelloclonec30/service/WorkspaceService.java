package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.WorkspaceCreateDto;
import by.tms.trelloclonec30.dto.WorkspaceResponseDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Workspace;
import by.tms.trelloclonec30.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private TeamService teamService;

    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    public Optional<Workspace> getWorkspaceById(Long id) {
        return workspaceRepository.findById(id);
    }

    public WorkspaceResponseDto createWorkspace(WorkspaceCreateDto workspaceDto, Account account) {
        Workspace workspace = new Workspace();
        workspace.setName(workspaceDto.getName());
        workspace.setAuthor(account);
        workspace = workspaceRepository.save(workspace);
        WorkspaceResponseDto workspaceResponseDto = new WorkspaceResponseDto();
        workspaceResponseDto.setIdWorkspace(workspace.getId());
        workspaceResponseDto.setNameWorkspace(workspace.getName());
        workspaceResponseDto.setNameAuthor(account.getUsername());
        return workspaceResponseDto;
    }

    public List<WorkspaceResponseDto> getAllWorkspacesByAccount(Account account) {
        List<Workspace> workspaces = workspaceRepository.findAllByAuthor_Id(account.getId());
        List<WorkspaceResponseDto> workspaceResponseDtos = new ArrayList<>();
        for (Workspace workspace : workspaces) {
            WorkspaceResponseDto workspaceResponseDto = new WorkspaceResponseDto();
            workspaceResponseDto.setIdWorkspace(workspace.getId());
            workspaceResponseDto.setNameWorkspace(workspace.getName());
            workspaceResponseDto.setNameAuthor(account.getUsername());
            workspaceResponseDto.setTeams(teamService.getAllTeams(workspace.getId()));
            workspaceResponseDtos.add(workspaceResponseDto);
        }
        return workspaceResponseDtos;
    }

}
