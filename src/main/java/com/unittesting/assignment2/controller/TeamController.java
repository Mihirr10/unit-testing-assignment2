package com.unittesting.assignment2.controller;

import com.unittesting.assignment2.entities.Team;
import com.unittesting.assignment2.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teams")
public class TeamController {

  @Autowired
  TeamService teamService;


  @GetMapping
  public ResponseEntity<List<Team>> getAllTeams() {
    List<Team> teams = teamService.getAllTeams();
    return new ResponseEntity<>(teams, HttpStatus.FOUND);
  }

  @PostMapping
  public ResponseEntity<Team> createTeam(@RequestBody Team team) {
    Team team1 = teamService.createTeam(team);

    return new ResponseEntity<>(team1, HttpStatus.CREATED);

  }

  @GetMapping("/{id}")
  public ResponseEntity<Team> getTeamWithId(@PathVariable int id) {
    Team teamWithId = teamService.getTeamWithId(id);
    return new ResponseEntity<>(teamWithId, HttpStatus.FOUND);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Team> updateTeam(@RequestBody Team team, @PathVariable int id) {
    Team updateTeam = teamService.updateTeam(team, id);
    return new ResponseEntity<>(updateTeam, HttpStatus.CREATED);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTeam(@PathVariable int id) {
    teamService.deleteTeam(id);
    return ResponseEntity.status(HttpStatus.OK).body("team deleted successfully");
  }
}
