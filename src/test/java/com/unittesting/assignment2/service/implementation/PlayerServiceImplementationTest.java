package com.unittesting.assignment2.service.implementation;

import com.unittesting.assignment2.entities.Player;
import com.unittesting.assignment2.entities.Team;
import com.unittesting.assignment2.repository.PlayerRepository;
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
class PlayerServiceImplementationTest {

  @Mock
  PlayerRepository playerRepository;

  @InjectMocks
  PlayerServiceImplementation playerServiceImplementation;

  Player player;
  Team team;

  List<Player> players = new ArrayList<>();

  @BeforeEach
  void setUp() {

    team = Team.builder().teamId(1).teamName("thunder").build();
    player = Player.builder().playerId(1).playerName("mihir").team(team).build();

    players.add(player);

  }

  @Test
  @DisplayName("Getting All Players:")
  void givenPlayersList_whenGetAllPlayers_ThenReturnPlayersList() {
    Team team1 = Team.builder().teamId(2).teamName("thunder").build();
    Player player1 = Player.builder().playerId(2).playerName("mihir").team(team).build();

    given(playerRepository.findAll()).willReturn(List.of(player, player1));

    List<Player> allPlayers = playerServiceImplementation.getAllPlayers();

    assertThat(allPlayers).isNotNull();
    assertThat(allPlayers.size()).isEqualTo(2);

  }

  @Test
  @DisplayName("Getting Player By id:")
  void givenPlayer_whenGetPlayersById_ThenReturnPlayer() {
    given(playerRepository.findById(1)).willReturn(Optional.of(player));
    Player player1 = playerServiceImplementation.getPlayerWithId(player.getPlayerId());
    assertThat(player1).isNotNull();
  }

  @Test
  @DisplayName("Create Player:")
  void givenPlayer_whenCreatePlayer_ThenReturnPlayer() {
    given(playerRepository.save(player)).willReturn(player);
    Player addPlayer = playerServiceImplementation.createPlayer(player);
    assertThat(addPlayer).isEqualTo(player);
  }

  @Test
  @DisplayName("Update Player By Id:")
  void givenPlayer_whenUpdatePlayer_ThenReturnPlayer() {
    given(playerRepository.findById(player.getPlayerId())).willReturn(Optional.ofNullable(player));
    player.setPlayerName("shailesh");
    given(playerRepository.save(player)).willReturn(player);
    Player updatedPlayer = playerServiceImplementation.updatePlayer(player, 1);
    assertThat(updatedPlayer.getPlayerName()).isEqualTo("shailesh");
  }

  @Test
  @DisplayName("Delete Player By Id:")
  void whenDeletePlayer_ThenReturnPlayer() {
    when(playerRepository.findById(player.getPlayerId())).thenReturn(Optional.of(player));
    doAnswer(Answers.CALLS_REAL_METHODS).when(playerRepository).deleteById(1);
    playerServiceImplementation.deletePlayer(1);
    verify(playerRepository).findById(player.getPlayerId());
    verify(playerRepository).delete(player);
  }
}