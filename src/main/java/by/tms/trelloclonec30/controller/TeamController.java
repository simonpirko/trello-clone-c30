package by.tms.trelloclonec30.controller;

import by.tms.trelloclonec30.dto.InviteToTeamDTO;
import by.tms.trelloclonec30.dto.TeamCreateDTO;
import by.tms.trelloclonec30.dto.TeamDto;
import by.tms.trelloclonec30.entity.Team;
import by.tms.trelloclonec30.repository.TeamRepository;
import by.tms.trelloclonec30.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;

@Operation(summary = "creating a team")
@PostMapping("/create")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamCreateDTO teamCreateDTO, Authentication auth) {
    TeamDto teamDto = teamService.createTeam(teamCreateDTO);
        return ResponseEntity.ok(teamDto);
    }
@Operation(summary = "find all team by workspace id")
@GetMapping("/workspace/{id}")
public ResponseEntity<List<TeamDto>> findAllByWorkspace(@PathVariable("id") Long id) {
   List<TeamDto> teamDTOs = teamService.getAllTeams(id);
   return ResponseEntity.ok(teamDTOs);
}
@Operation(summary = "invite to team by account name and team id")
    @PostMapping("/invite/{id}")
    public ResponseEntity<TeamDto> invite(@PathVariable("id") Long id, @RequestBody InviteToTeamDTO inviteToTeamDTO, Authentication auth) {
   TeamDto teamDTO = teamService.invite(inviteToTeamDTO, id);
    return ResponseEntity.ok(teamDTO);
    }
}
