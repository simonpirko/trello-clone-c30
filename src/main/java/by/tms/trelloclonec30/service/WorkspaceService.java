package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.WorkspaceCreateDto;
import by.tms.trelloclonec30.dto.WorkspaceResponseDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Role;
import by.tms.trelloclonec30.entity.Roles;
import by.tms.trelloclonec30.entity.Workspace;
import by.tms.trelloclonec30.repository.RolesRepository;
import by.tms.trelloclonec30.repository.WorkspaceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private RolesRepository rolesRepository;

    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    public Optional<Workspace> getWorkspaceById(Long id) {
        return workspaceRepository.findById(id);
    }
@Transactional
    public WorkspaceResponseDto createWorkspace(WorkspaceCreateDto workspaceDto, Account account) {
        Workspace workspace = new Workspace();
        workspace.setName(workspaceDto.getName());
        Roles role = new Roles();
        role.setAccount(account);
        role.setRole(Role.AUTHOR_WORKSPACE);
       role = rolesRepository.save(role);
       Set<Roles> rolesSet = new HashSet<>();
       rolesSet.add(role);
       workspace.setRoles(rolesSet);
    workspace = workspaceRepository.save(workspace);
        WorkspaceResponseDto workspaceResponseDto = new WorkspaceResponseDto();
        workspaceResponseDto.setIdWorkspace(workspace.getId());
        workspaceResponseDto.setNameWorkspace(workspace.getName());
        workspaceResponseDto.setNameAuthor(account.getUsername());
        return workspaceResponseDto;
    }

  /*  public List<WorkspaceResponseDto> getAllWorkspacesByAccount(Account account) {
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
    }*/

    public WorkspaceResponseDto edit(Workspace workspace, Account account)  {
        if (checkRoles(workspace, account)) {
            workspace = workspaceRepository.save(workspace);
            WorkspaceResponseDto workspaceResponseDto = new WorkspaceResponseDto();
            workspaceResponseDto.setIdWorkspace(workspace.getId());
            workspaceResponseDto.setNameWorkspace(workspace.getName());
            workspaceResponseDto.setNameAuthor(account.getUsername());
            return workspaceResponseDto;
        }
        else{
            throw new IllegalAccessError("Not allowed to edit workspace");
        }
    }

private boolean checkRoles(Workspace workspace, Account account) {
    Set<Roles> rolesSet = workspace.getRoles();
   Roles roles = rolesSet.iterator().next();
    return roles.getAccount().equals(account);
}
}
