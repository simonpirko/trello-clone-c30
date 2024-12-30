package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.workspace.WorkspaceCreateDto;
import by.tms.trelloclonec30.dto.workspace.WorkspaceResponseDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Role;
import by.tms.trelloclonec30.entity.Roles;
import by.tms.trelloclonec30.entity.Workspace;
import by.tms.trelloclonec30.repository.RolesRepository;
import by.tms.trelloclonec30.repository.WorkspaceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<WorkspaceResponseDto> getAllWorkspacesByAccount(Account account) {
        Set<Roles> rolesSet = rolesRepository.findAllByAccountAndRole(account, Role.AUTHOR_WORKSPACE);
        List<Workspace> workspaces = workspaceRepository.findAllByRolesIn(rolesSet);
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

    public void deleteWorkspaceById(Long id, Account account) {
        Optional<Workspace> workspaceOptional = workspaceRepository.findById(id);
        if (workspaceOptional.isPresent()) {
            Workspace workspace = workspaceOptional.get();
            if (checkRoles(workspace, account)) {
                workspaceRepository.delete(workspace);
            } else {
                throw new IllegalAccessError("Not allowed to delete workspace");
            }
        } else {
            throw new EntityNotFoundException("Workspace with id " + id + " not found");
        }
    }

    public boolean checkRoles(Workspace workspace, Account account) {
        Set<Roles> rolesSet = workspace.getRoles();
        Roles roles = rolesSet.iterator().next();
        return roles.getAccount().equals(account);
    }
}
