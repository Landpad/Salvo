package com.accenture.Salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api")
public class SalvoController {


    @Autowired
    private GameRepository repoGames;
    Set<GameRepository> gamesid;


    @RequestMapping("/games")
    public List<Object> getId (){
        return repoGames.findAll().stream()
                .map(this::getDTO)
                .collect(Collectors.toList());
    }
        private Map<String, Object> getDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id",game.getid());
        dto.put("created",game.getCreationDate());
        return dto;
    }

    @Autowired
    private PlayerRepository repoPlayers;
    Set<PlayerRepository> playerid;


    @RequestMapping("/players")
    public List<Object> getPlayerid (){
        return repoPlayers.findAll().stream()
                .map(this::playerdto)
                .collect(Collectors.toList());
    }
    private Map<String, Object> playerdto(Player players) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id",players.getId());
        dto.put("First Name",players.getFirstName());
        dto.put("Last Name",players.getLastName());
        dto.put("User Name",players.getUserName());
        return dto;
    }
    @Autowired
    private GamePlayerRepository repoGameplayers;
    Set<GamePlayerRepository> gameplayerid;


    @RequestMapping("/gameplayers")
    public List<Object> getgameplayerid (){
        return repoGameplayers.findAll().stream()
                .map(this::gameplayer)
                .collect(Collectors.toList());
    }

    private Map<String, Object> gameplayer(GamePlayer gamePlayer,Game games, Player players) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id",gamePlayer.getId());
        dto.put("Games",games.gamePlayers);
        dto.put("Player 1", players.getUserName());
        dto.put("Player 2",players.getUserName());
        return dto;


}}