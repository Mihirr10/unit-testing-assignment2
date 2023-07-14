package com.unittesting.assignment2.service.implementation;

import com.unittesting.assignment2.entities.Player;
import com.unittesting.assignment2.entities.Team;
import com.unittesting.assignment2.repository.TeamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class TeamServiceImplementationTest {

  @Mock
  TeamRepository teamRepository;

  @InjectMocks
  TeamServiceImplementation teamServiceImplementation;

  Team team;
  Team team1;

  Player player;
  Player player1;

  List<Team> teams = new ArrayList<>();


  @BeforeEach
  void setUp() {
    player = Player.builder().playerId(1).playerName("mihir").team(team).build();
    player1 = Player.builder().playerId(2).playerName("yash").team(team).build();
    team = Team.builder().teamId(1).teamName("superlative").players(List.of(player, player1)).build();
    team1=Team.builder().teamId(2).teamName("titans").players(List.of(player)).build();
    teams.add(team);
    teams.add(team1);

  }

  @AfterEach
  void tearDown() {
    teams.clear();
  }

  @Test
  @DisplayName("Getting All Teams:")
  void givenTeamList_whenGetAllTeam_ReturnTeamList() {

    given(teamRepository.findAll()).willReturn(List.of(team,team1));

    List<Team> getAllTeams = teamServiceImplementation.getAllTeams();

    assertThat(getAllTeams).isNotNull();
    assertThat(getAllTeams.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("Getting Team with id:")
  void givenTeam_whenGetTeamWithId_ReturnTeam() {
    given(teamRepository.findById(1)).willReturn(Optional.ofNullable(team));
    Team teamWithId = teamServiceImplementation.getTeamWithId(team.getTeamId());
    assertThat(teamWithId.getTeamId()).isEqualTo(team.getTeamId());
  }

  @Test
  @DisplayName("create Team:")
  void givenTeam_whenCreateTeam_ReturnTeam() {
    given(teamRepository.save(team)).willReturn(team);
    Team addTeam = teamServiceImplementation.createTeam(team);
    assertThat(addTeam).isEqualTo(team);
  }

  @Test
  @DisplayName("Update Team with id:")
  void givenUpdateTeam_whenUpdateTeam_ReturnUpdatedTeam() {
    given(teamRepository.findById(team.getTeamId())).willReturn(Optional.ofNullable(team));
    team.setTeamName("barcelona");
    given(teamRepository.save(team)).willReturn(team);
    Team updateTeam = teamServiceImplementation.updateTeam(team, 1);
    assertThat(updateTeam.getTeamName()).isEqualTo("barcelona");
  }

  @Test
  @DisplayName("Delete Team with id:")
  void givenDeleteTeam_whenDeleteTeam_ReturnDeletedTeam() {
    when(teamRepository.findById(team.getTeamId())).thenReturn(Optional.of(team));
    doAnswer(Answers.CALLS_REAL_METHODS).when(teamRepository).deleteById(1);
    teamServiceImplementation.deleteTeam(1);
    verify(teamRepository).findById(team.getTeamId());
    verify(teamRepository).delete(team);
  }
}