package com.unittesting.assignment2.controller;

import com.unittesting.assignment2.entities.Player;
import com.unittesting.assignment2.exception.PlayerNotFound;
import com.unittesting.assignment2.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/players")
public class PlayerController {

  @Autowired
  PlayerService playerService;

//  @GetMapping("/playerWithTeam")
//  public List<PlayerDto> getPlayerWithTeam() {
//    return playerService.getPlayerWithTeam();
//  }

  @GetMapping
  public ResponseEntity<List<Player>> getAllPlayers() {
    List<Player> players = playerService.getAllPlayers();
    if (players != null) {
      return new ResponseEntity<>(players, HttpStatus.FOUND);
    } else {
      throw new PlayerNotFound("Player Not Found");
    }

  }
//
//  @GetMapping("/{playerName}")
//    public ResponseEntity<Player> getPlayerByPlayerName(@PathVariable String playerName){
//    Player playerByPlayerName = playerService.getPlayerByPlayerName(playerName);
//    if (playerByPlayerName != playerService.getPlayerByPlayerName(playerName)) {
//      throw new PlayerNotFound("Player Not Found");
//    }
//    return new ResponseEntity<>(playerByPlayerName,HttpStatus.FOUND);
//  }


  @PostMapping
  public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
    Player player1 = playerService.createPlayer(player);

    if (player1 != null) {
      return new ResponseEntity<>(player1, HttpStatus.CREATED);
    } else {
      throw new PlayerNotFound("Player Not Found");
    }

  }

  @GetMapping("/{id}")
  public ResponseEntity<Player> getPlayerWithId(@PathVariable int id) {
    Player playerWithId = playerService.getPlayerWithId(id);
    if (playerWithId != null) {
      return new ResponseEntity<>(playerWithId, HttpStatus.FOUND);
    } else {
      throw new PlayerNotFound("player not found");
    }

  }

  @PutMapping("/{id}")
  public ResponseEntity<Player> updatePlayer(@RequestBody Player player, @PathVariable int id) {
    Player updatePlayer = playerService.updatePlayer(player, id);
    if (updatePlayer != null) {
      return new ResponseEntity<>(updatePlayer, HttpStatus.CREATED);
    } else {
      throw new PlayerNotFound("Player Not Found");
    }

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePlayer(@PathVariable int id) {
    try {
      playerService.deletePlayer(id);
    } catch (PlayerNotFound playerNotFound) {
      throw new PlayerNotFound("Player Not Found");
    }
    return ResponseEntity.status(HttpStatus.OK).body("player deleted successfully");
  }
}
