package com.accenture.Salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class SalvoController {


    @Autowired
    GameRepository repoGames;

    @Autowired
    GamePlayerRepository repoGameplayer;

    @Autowired
    PlayerRepository repoPlayer;


    @RequestMapping("/games")
    public Object games() {
        return guestDTO();
    }

    @RequestMapping("/game_view/{id}")
    public Object getGameById(@PathVariable("id") String gamePlayerId) {
        Map<String, Object> dto = new LinkedHashMap<>();
        GamePlayer gameplayer = repoGameplayer.findById(Long.parseLong(gamePlayerId));
        dto.put("id", gameplayer.getId());
        dto.put("created", gameplayer.getGame().getCreationDate());
        dto.put("gamePlayers", this.gamePlayer(gameplayer.getGames()));
        dto.put("ships", gameplayer.getGamePlayerShipsDTO());
        dto.put("salvoes", gameplayer.getGames().getGameSalvosDTO());
        return dto;

    }

    @RequestMapping("/leaderBoard")
    public List<Object> getLeaderBoard() {
        List<Player> score = repoPlayer.findAll();
        return score.stream().map(player -> player.getAllScoreDTO()).collect(Collectors.toList());
    }


    private Map<String, Object> getPlayer(Player player) {
        Map<String, Object> pdto = new LinkedHashMap<String, Object>();
        pdto.put("id", player.getId());
        pdto.put("email", player.getemail());
        return pdto;
    }

    private List<Object> gamePlayer(Game game) {
        return game.getGamePlayers().stream().map(gamePlayer -> {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("id", gamePlayer.getId());
            dto.put("player", getPlayer(gamePlayer.getPlayer()));
            dto.put("joindate", gamePlayer.getJoinDate());
            return dto;
        }).collect(Collectors.toList());
    }


    public Object guestDTO(){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("player", this.isGuest(null));
        dto.put("games", repoGames.findAll().stream().map(s->s.getDTO()).collect(toList()));
        return dto;
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

}











