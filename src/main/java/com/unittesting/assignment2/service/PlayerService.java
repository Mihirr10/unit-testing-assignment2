package com.unittesting.assignment2.service;

import com.unittesting.assignment2.entities.Player;

import java.util.List;

public interface PlayerService {

//  public List<PlayerDto> getPlayerWithTeam();

  public List<Player> getAllPlayers();

  public Player getPlayerWithId(int id);

  public Player createPlayer(Player player);

  public Player updatePlayer(Player player, int id);

  public void deletePlayer(int id);

//  Player getPlayerByPlayerName(String playerName);
}
