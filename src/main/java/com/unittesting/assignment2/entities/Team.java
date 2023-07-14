package com.unittesting.assignment2.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
@Builder
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "player")
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int teamId;
  private String teamName;

  @OneToMany(mappedBy = "team")
  @JsonIgnore
  private List<Player> players;
}
