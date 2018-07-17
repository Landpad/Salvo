package com.accenture.Salvo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player players;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game games;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Ship> ships;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Salvo> salvos;

    public GamePlayer() {
    }

    public GamePlayer(Game games, Player players, Date joinDate) {
        this.games = games;
        this.players = players;
        this.joinDate = new Date();
    }

    public long getId() {
        return id;
    }

    public Player getPlayers() {
        return players;
    }

    public void setPlayer(Player players) {
        this.players = players;
    }

    public Player getPlayer() {
        return players;
    }

    public Game getGames() {
        return games;
    }

    public Game getGame() {
        return games;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public void setSalvos(Set<Salvo> salvos) {
        this.salvos = salvos;
    }

    public List<Object> getGamePlayerShipsDTO() {
        return ships.stream().map(s -> s.getShipDTO()).collect(toList());
    }

    public List<Object> getGamePlayerSalvosDTO() {
        return salvos.stream().map(s -> s.getSalvoDTO()).collect(toList());
    }


    public Object gamePlayer() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("player", players.getPlayer());
        dto.put("joindate", this.getJoinDate());
        return dto;
    }

    public void addShips(List<Ship> ships) {
        ships.forEach(ship -> {
            ship.setGameplayers(this);
            ships.add(ship);
        });
    }

    public void addSalvo(Salvo salvo) {
        salvo.setGameplayers(this);
        salvos.add(salvo);
    }

    public GamePlayer opponentPlayer() {
        return games.getGamePlayers().stream().filter(s -> s.getPlayer().getId() != getPlayer().getId()).findFirst().get();
    }

    public Map<String, Object> hitsdto() {
        Map<String, Object> hits = new LinkedHashMap<>();
        hits.put("hits", )


        public List<Object> self () {
            Map<String, Object> selfie = new LinkedHashMap<>();
            opponentPlayer().getSalvos().forEach(salvo ->
                    selfie.put("self", salvo.getTurn()));
            return selfie;
        }
    }}
















