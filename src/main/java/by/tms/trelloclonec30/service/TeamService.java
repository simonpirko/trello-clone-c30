package by.tms.trelloclonec30.service;

import by.tms.trelloclonec30.dto.TeamDto;
import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Team;
import by.tms.trelloclonec30.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<TeamDto> getAllTeams(Long workspaceId) {
        List<Team> teams = teamRepository.findAllByWorkspace_Id(workspaceId);
        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : teams) {
            TeamDto teamDto = new TeamDto();
            teamDto.setTeamName(team.getName());
            List<String> members = new ArrayList<>();
            for (Account account : team.getAccounts()) {
                members.add(account.getUsername());
            }
            teamDto.setMembers(members);
            teamDtos.add(teamDto);
        }
        return teamDtos;
    }
}
