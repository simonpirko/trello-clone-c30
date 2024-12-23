package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.InviteToTeamDTO;
import by.tms.trelloclonec30.dto.TeamCreateDTO;
import by.tms.trelloclonec30.dto.TeamDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Team;
import by.tms.trelloclonec30.entity.Workspace;
import by.tms.trelloclonec30.repository.AccountRepository;
import by.tms.trelloclonec30.repository.TeamRepository;
import by.tms.trelloclonec30.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private AccountRepository accountRepository;

    public TeamDto createTeam(TeamCreateDTO teamCreateDTO) {
        Team team = new Team();
        Workspace workspace = workspaceRepository.findById(teamCreateDTO.getIdWorkspace()).get();
        team.setName(teamCreateDTO.getTeamName());
        team.setWorkspace(workspace);
        team = teamRepository.save(team);
        TeamDto teamDto = convertTeamToTeamDto(team);
        return teamDto;
    }

    public List<TeamDto> getAllTeams(Long workspaceId) {
        List<Team> teams = teamRepository.findAllByWorkspace_Id(workspaceId);
        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : teams) {
            teamDtos.add(convertTeamToTeamDto(team));
        }
        return teamDtos;
    }
  
    public TeamDto convertTeamToTeamDto(Team team) {
        TeamDto teamDto = new TeamDto();
        teamDto.setTeamName(team.getName());
        teamDto.setId(team.getId());
        List<String> members = new ArrayList<>();
        if (team.getAccounts() != null) {
            for (Account account : team.getAccounts()) {
                members.add(account.getUsername());
            }
        }
        teamDto.setMembers(members);
        return teamDto;
    }
  
    public TeamDto invite(InviteToTeamDTO inviteToTeamDTO) {
        Team team = teamRepository.findById(inviteToTeamDTO.getIdTeam()).get();
        Account account = accountRepository.findByUsername(inviteToTeamDTO.getAccountName()).get();
        team.getAccounts().add(account);
        teamRepository.save(team);
        return convertTeamToTeamDto(team);
    }
}
