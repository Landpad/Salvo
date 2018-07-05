package com.accenture.Salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
    public Object games(Authentication authentication) {
        Map<String, Object> gamedto = new LinkedHashMap<>();
        if (isGuest (authentication) == true){
            gamedto.put("player", "Guest");
            return gamedto;
        } else {
            gamedto.put("player", this.playerautenticated(authentication));
            gamedto.put("games", repoGames.findAll().stream().map(s->s.getDTO()).collect(toList()));
            return gamedto;
        }
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

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createPlayer(@RequestParam String name, String username, String password) {
        if (name.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "No name"), HttpStatus.FORBIDDEN);
        }
        Player player = repoPlayer.findByUserName(name);
        if (player != null) {
            return new ResponseEntity<>(makeMap("error", "Username already exists"), HttpStatus.CONFLICT);
        }
        Player newPlayer = repoPlayer.save(new Player(username,name,password));
        return new ResponseEntity<>(makeMap("id", newPlayer.getId()), HttpStatus.CREATED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
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

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private Object playerautenticated(Authentication authentication){
        Map<String, Object> playerdto = new LinkedHashMap<>();
        playerdto.put("id", repoPlayer.findByUserName(authentication.getName()).getId());
        playerdto.put("name", repoPlayer.findByUserName(authentication.getName()).getemail());
        return playerdto;
    }

}










