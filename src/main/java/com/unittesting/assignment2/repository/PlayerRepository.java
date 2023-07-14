package com.unittesting.assignment2.repository;

import com.unittesting.assignment2.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

  Player findByPlayerName(String playerName);
}
