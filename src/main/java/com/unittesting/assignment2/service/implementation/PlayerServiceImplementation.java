package com.unittesting.assignment2.service.implementation;

import com.unittesting.assignment2.entities.Player;
import com.unittesting.assignment2.exception.PlayerNotFound;
import com.unittesting.assignment2.repository.PlayerRepository;
import com.unittesting.assignment2.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImplementation implements PlayerService {

  @Autowired
  PlayerRepository playerRepository;

  public PlayerServiceImplementation(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

//  public List<PlayerDto> getPlayerWithTeam() {
//    return playerRepository.findAll().stream().map(p -> new PlayerDto(p.getPlayerName(), p.getTeam().getTeamName())).collect(Collectors.toList());
//  }


  public List<Player> getAllPlayers() {
    return playerRepository.findAll();
  }

  public Player getPlayerWithId(int id) {

    Optional<Player> player = playerRepository.findById(id);

    if (player.isPresent()) {
      return player.get();
    } else {
      throw new PlayerNotFound("Player not found");
    }
  }

  @Override
  public Player createPlayer(Player player) {

    if (player != null) {
      return playerRepository.save(player);
    } else {
      throw new PlayerNotFound("failed to create the player");
    }
  }

  @Override
  public Player updatePlayer(Player player, int id) {
    Player player1 = playerRepository.findById(id).orElse(null);


    if (player1 == null) {
      throw new PlayerNotFound("failed to update student");
    } else {
      player.setPlayerId(id);
    }
    return playerRepository.save(player);
  }

  @Override
  public void deletePlayer(int id) {
    Optional<Player> player2 = playerRepository.findById(id);

    if (player2.isPresent()) {
      playerRepository.delete(player2.get());
    } else {
      throw new PlayerNotFound("failed to delete student !! please enter valid id");
    }
  }

//  @Override
//  public Player getPlayerByPlayerName(String playerName) {
//    return playerRepository.findByPlayerName(playerName);
//  }


}
