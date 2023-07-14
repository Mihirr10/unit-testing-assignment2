package com.unittesting.assignment2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unittesting.assignment2.entities.Player;
import com.unittesting.assignment2.entities.Team;
import com.unittesting.assignment2.service.TeamService;
import org.junit.jupiter.api.*;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;

@WebMvcTest(TeamController.class)
class TeamControllerTest {

  @MockBean
  TeamService teamService;

  @Autowired
  MockMvc mockMvc;

  Team team;
  Player player;
  Player player1;

  List<Team> teams = new ArrayList<>();

  static String getJsonObject(Object object) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String requestJson = mapper.writeValueAsString(object);
    return requestJson;
  }

  @BeforeEach
  void setUp() {
    player = Player.builder().playerId(1).playerName("Suarez").build();
    player1 = Player.builder().playerId(2).playerName("steven").build();
    team = Team.builder().teamId(1).teamName("LiverPool").players(List.of(player, player1)).build();
    teams.add(team);
  }

  @AfterEach
  void tearDown() {
    teams.clear();
  }

  @Nested
  @DisplayName("Display All Teams")
  class DisplayAllTeams {

    @Test
    @DisplayName("positive scenario")
    void whenGetAllTeams_thenReturnTeams_positive() throws Exception {
      when(teamService.getAllTeams()).thenReturn(teams);
      mockMvc.perform(get("/api/v1/teams")).andDo(print()).andExpect(status().isFound());
    }

    @Test
    @DisplayName("negative scenario")
    void whenGetAllTeams_thenReturnTeams_negative() throws Exception {
      when(teamService.getAllTeams()).thenReturn(teams);
      mockMvc.perform(get("/api/v1/teams/")).andDo(print()).andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("Create Team:")
  class CreateTeam {
    @Test
    @DisplayName("positive scenario")
    void whenCreateTeam_thenReturnTeam_positive() throws Exception {
      String jsonObject = getJsonObject(team);
      when(teamService.createTeam(team)).thenReturn(team);
      mockMvc.perform(post("/api/v1/teams").contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("negative scenario")
    void whenCreateTeam_thenReturnTeam_Negative() throws Exception {
      String jsonObject = getJsonObject(team);
      when(teamService.createTeam(team)).thenReturn(null);
      mockMvc.perform(post("/api/v1/team").contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andDo(print()).andExpect(status().isNotFound());
    }

  }

  @Nested
  @DisplayName("Display Team By Id:")
  class DisplayTeamById {
    @Test
    @DisplayName("positive scenario")
    void whenGetTeamWithId_thenReturnTeam_positive() throws Exception {
      when(teamService.getTeamWithId(1)).thenReturn(team);
      mockMvc.perform(get("/api/v1/teams/1")).andDo(print()).andExpect(status().isFound());
    }

    @Test
    @DisplayName("negative scenario")
    void whenGetTeamWithId_thenReturnTeam_negative() throws Exception {
      when(teamService.getTeamWithId(1)).thenReturn(team);
      mockMvc.perform(get("/api/v1/team/1")).andDo(print()).andExpect(status().isNotFound());
    }


  }

  @Nested
  @DisplayName("Update teams:")
  class UpdateTeam {
    @Test
    @DisplayName("positive scenario")
    void whenUpdateTeam_thenReturnUpdatedTeam_positive() throws Exception {
      Team updateTeam = Team.builder().teamId(team.getTeamId()).teamName("Madrid").players(List.of(player, player1)).build();
      String jsonObject = getJsonObject(updateTeam);
      when(teamService.updateTeam(updateTeam, 1)).thenReturn(updateTeam);
      mockMvc.perform(put("/api/v1/teams/1").contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("negative scenario")
    void whenUpdateTeam_thenReturnUpdatedTeam_negative() throws Exception {
      Team updateTeam = Team.builder().teamId(team.getTeamId()).teamName("Madrid").players(List.of(player, player1)).build();
      String jsonObject = getJsonObject(updateTeam);
      when(teamService.updateTeam(updateTeam, 1)).thenReturn(updateTeam);
      mockMvc.perform(put("/api/v1/team/1").contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andDo(print()).andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("Delete Team:")
  class DeleteTeam {

    @Test
    @DisplayName("positive scenario")
    void whenDeleteTeam_thenReturnDeletedTeam_positive() throws Exception {
      doAnswer(Answers.CALLS_REAL_METHODS).when(teamService).deleteTeam(1);
      mockMvc.perform(delete("/api/v1/teams/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("negative scenario")
    void whenDeleteTeam_thenReturnDeletedTeam_negative() throws Exception {
      doAnswer(Answers.CALLS_REAL_METHODS).when(teamService).deleteTeam(1);
      mockMvc.perform(delete("/api/v1/team/1")).andDo(print()).andExpect(status().isNotFound());
    }
  }
}