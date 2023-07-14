package com.unittesting.assignment2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unittesting.assignment2.entities.Player;
import com.unittesting.assignment2.entities.Team;
import com.unittesting.assignment2.service.PlayerService;
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


@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

  @MockBean
  PlayerService playerService;

  @Autowired
  MockMvc mockMvc;

  Player player;
  Team team;

  List<Player> playerList = new ArrayList<>();

  static String getJsonObject(Object object) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String requestJson = mapper.writeValueAsString(object);
    return requestJson;
  }

  @BeforeEach
  void setUp() {
    team = Team.builder().teamId(1).teamName("superlative").build();
    player = Player.builder().playerId(1).playerName("mihir").team(team).build();

    playerList.add(player);

  }

  @AfterEach
  void tearDown() {
    playerList.clear();
  }

  @Nested
  @DisplayName("Display All Players:")
  class DisplayAllPlayers {

    @Test
    @DisplayName("positive scenario")
    void when_getPlayers_thenReturnAllPlayers_positive() throws Exception {
      when(playerService.getAllPlayers()).thenReturn(playerList);
      mockMvc.perform(get("/api/v1/players")).andDo(print()).andExpect(status().isFound());
    }

    @Test
    @DisplayName("negative scenario")
    void when_getPlayers_thenReturnAllPlayers_negative() throws Exception {
      when(playerService.getAllPlayers()).thenReturn(playerList);
      mockMvc.perform(get("/api/v1/players/")).andDo(print()).andExpect(status().isNotFound());
    }

  }

  @Nested
  @DisplayName("Add Players:")
  class addPlayers {


    @Test
    @DisplayName("positive Scenario")
    void whenCreatePlayer_thenReturn_positive() throws Exception {
      String jsonObject = getJsonObject(player);

      when(playerService.createPlayer(player)).thenReturn(player);
      mockMvc.perform(post("/api/v1/players")
              .contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("negative Scenario")
    void whenCreatePlayer_thenReturn_negative() throws Exception {
      String jsonObject = getJsonObject(player);

      when(playerService.createPlayer(player)).thenReturn(null);
      mockMvc.perform(post("/api/v1/players")
              .contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andDo(print()).andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("Display Player By Id:")
  class DisplayPlayersById {


    @Test
    @DisplayName("positive scenario")
    void whenGetPlayerId_thenReturnPlayer_positive() throws Exception {
      when(playerService.getPlayerWithId(1)).thenReturn(player);
      mockMvc.perform(get("/api/v1/players/1")).andDo(print()).andExpect(status().isFound());
    }


    @Test
    @DisplayName("negative scenario")
    void whenGetPlayerId_thenReturnPlayer_negative() throws Exception {
      when(playerService.getPlayerWithId(1)).thenReturn(player);
      mockMvc.perform(get("/api/v1/players/2")).andDo(print()).andExpect(status().isNotFound());
    }


  }

  @Nested
  @DisplayName("Update Players:")
  class UpdatePlayers {


    @Test
    @DisplayName("positive Scenario")
    void whenUpdatePlayer_thenReturn_positive() throws Exception {
      Player updatedPlayer = Player.builder().playerId(player.getPlayerId()).playerName("shailesh").team(team).build();
      String jsonObject = getJsonObject(updatedPlayer);

      when(playerService.updatePlayer(updatedPlayer, 1)).thenReturn(updatedPlayer);
      mockMvc.perform(put("/api/v1/players/1")
              .contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("negative Scenario")
    void whenUpdatePlayer_thenReturn_negative() throws Exception {
      Player updatedPlayer = Player.builder().playerId(player.getPlayerId()).playerName("shailesh").team(team).build();
      String jsonObject = getJsonObject(updatedPlayer);

      when(playerService.updatePlayer(updatedPlayer, 1)).thenReturn(updatedPlayer);
      mockMvc.perform(put("/api/v1/players/2").
              contentType(MediaType.APPLICATION_JSON)
              .content(jsonObject)).andDo(print()).andExpect(status().isNotFound());
    }

  }

  @Nested
  @DisplayName("Delete Player:")
  class DeletePlayers {
    @Test
    @DisplayName("positive scenario")
    void whenDeletePlayer_thenReturn_positive() throws Exception {

      doAnswer(Answers.CALLS_REAL_METHODS).when(playerService).deletePlayer(1);
      mockMvc.perform(delete("/api/v1/players/1")).andDo(print()).andExpect(status().isOk());

    }

    @Test
    @DisplayName("negative scenario")
    void whenDeletePlayer_thenReturn_negative() throws Exception {

      doAnswer(Answers.CALLS_REAL_METHODS).when(playerService).deletePlayer(1);
      mockMvc.perform(delete("/api/v1/players/")).andDo(print()).andExpect(status().isNotFound());

    }

  }


}