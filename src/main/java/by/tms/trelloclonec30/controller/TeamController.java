package by.tms.trelloclonec30.controller;

import by.tms.trelloclonec30.dto.InviteToTeamDTO;
import by.tms.trelloclonec30.dto.TeamCreateDTO;
import by.tms.trelloclonec30.dto.TeamDto;
import by.tms.trelloclonec30.entity.Team;
import by.tms.trelloclonec30.repository.TeamRepository;
import by.tms.trelloclonec30.service.TeamService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;


@PostMapping("/create")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamCreateDTO teamCreateDTO, Authentication auth) {
    TeamDto teamDto = teamService.createTeam(teamCreateDTO);
        return ResponseEntity.ok(teamDto);
    }
@PostMapping("/show")
public ResponseEntity<List<TeamDto>> show(@RequestBody TeamCreateDTO teamCreateDTO) {
   List<TeamDto> teamDTOs = teamService.getAllTeams(teamCreateDTO.getIdWorkspace());
   return ResponseEntity.ok(teamDTOs);
}
    @PostMapping("/invite")
    public ResponseEntity<TeamDto> invite(@RequestBody InviteToTeamDTO inviteToTeamDTO, Authentication auth) {
   TeamDto teamDTO = teamService.invite(inviteToTeamDTO);
    return ResponseEntity.ok(teamDTO);
    }
}
