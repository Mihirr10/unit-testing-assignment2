package com.unittesting.assignment2.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@AllArgsConstructor
@NoArgsConstructor

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "player")
@Entity
@Table(name = "player")
@Builder
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int playerId;
  private String playerName;

  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;
}
