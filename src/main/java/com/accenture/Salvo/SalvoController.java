package com.accenture.Salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
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
        if (isGuest (authentication)){
            gamedto.put("player", "Guest");
        } else {
            gamedto.put("player", this.playerAutenticated(authentication));

        }
        gamedto.put("games", repoGames.findAll().stream().map(s->s.getDTO()).collect(toList()));
        return gamedto;


}

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createNewGame(Authentication authentication) {
        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "Please, log in"), HttpStatus.UNAUTHORIZED);
        }else if(repoPlayer.findByUserName((authentication.getName()))!= null)
        {
            Date date = new Date();
            Game game = new Game(new Date());
            GamePlayer gamePlayer = new GamePlayer(game, repoPlayer.findByUserName(authentication.getName()), date);
            repoGames.save(game);
            repoGameplayer.save(gamePlayer);
            return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(makeMap("error","Doesn´t exist"),HttpStatus.NOT_FOUND);


    }

    @RequestMapping (path = "/game/{id}/players", method = RequestMethod.POST)
    public Object joinGame (@PathVariable("id") Long gameId) {
        Player player = this.getAuthPlayer();
        if (player == null) {
            return new ResponseEntity<>(makeMap("error", "Please, log in"), HttpStatus.UNAUTHORIZED);
        }
        Game game = repoGames.findOne(gameId);
        if (game == null) {
            return new ResponseEntity<>(makeMap("error", "Incorrect game id"), HttpStatus.FORBIDDEN);
        }
        if (game.getPlayers().size() == 2) {         //posible inminente
            return new ResponseEntity<>(makeMap("error", "Game is full"), HttpStatus.FORBIDDEN);
        }

        Date date = new Date();
        GamePlayer gamePlayer = new GamePlayer(game, player, date);
        repoGameplayer.save(gamePlayer);
        return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);

    }

    @RequestMapping("/game_view/{id}")
    public Object gameView(@PathVariable long id, Authentication authentication) {
        GamePlayer gamePlayer = repoGameplayer.findOne(id);
        if (gamePlayer != null) {
            if (gamePlayer.getPlayer().getusername() == playerAutentication(authentication)) {
                return getGameById(gamePlayer);

            }
            return new ResponseEntity<>(this.makeMap("error", "Don´t cheat"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(this.makeMap("error", "Please, log in"), HttpStatus.BAD_REQUEST);
    }
    @RequestMapping("/leaderBoard")
    public List<Object> getLeaderBoard() {
        List<Player> score = repoPlayer.findAll();
        return score.stream().map(player -> player.getAllScoreDTO()).collect(Collectors.toList());
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createPlayer(@RequestParam String username, String password) {
        if (username.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "No name"), HttpStatus.BAD_REQUEST);
        }
        Player player = repoPlayer.findByUserName(username);
        if (player != null) {
            return new ResponseEntity<>(makeMap("error", "Username already exists"), HttpStatus.CONFLICT);
        }
        Player newPlayer = repoPlayer.save(new Player(username,password));
        return new ResponseEntity<>(makeMap("id", newPlayer.getId()), HttpStatus.CREATED);
    }

    @RequestMapping (path = "/game/{id}/players", method = RequestMethod.POST)
    public Object ships (@PathVariable("id") Long shipId) {
        Player player = this.getAuthPlayer();
        if (player == null) {
            return new ResponseEntity<>(makeMap("error", "Please, log in"), HttpStatus.UNAUTHORIZED);
        }
        Game game = repoGames.findOne(shipId);
        if (game == null) {
            return new ResponseEntity<>(makeMap("error", "Incorrect game id"), HttpStatus.FORBIDDEN);
        }
        if (game.getDTO().size() == 2) {
            return new ResponseEntity<>(makeMap("error", "Game is full"), HttpStatus.FORBIDDEN);
        }

        Date date = new Date();
        GamePlayer gamePlayer = new GamePlayer(game, player, date);
        repoGameplayer.save(gamePlayer);
        return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);

    }

    @RequestMapping (path = "/games/players/{gamePlayerId}/ships", method = RequestMethod.GET)
    public Object getShips (@PathVariable("id") Long shipID) {
        Map<String,Object> shipPlacement = new LinkedHashMap<>();
        Player player = this.getAuthPlayer();
        GamePlayer gamePlayer = repoGameplayer.findOne(shipID);
        if (getAuthPlayer() == null || getAuthPlayer().getId() != gamePlayer.getPlayers().getId()) {
            return new ResponseEntity<>(makeMap("error", "Please log in"), HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer == null) {         //posible inminente
            return new ResponseEntity<>(makeMap("error", "Game Player Doesn´t exist"), HttpStatus.z);
        }

        Date date = new Date();
        GamePlayer gamePlayer = new GamePlayer(game, player, date);
        repoGameplayer.save(gamePlayer);
        return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);

    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private Map<String, Object> getPlayer(Player player) {
        Map<String, Object> pdto = new LinkedHashMap<String, Object>();
        pdto.put("id", player.getId());
        pdto.put("email", player.getusername());
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

    private Object playerAutenticated(Authentication authentication){
        Map<String, Object> playerdto = new LinkedHashMap<>();
        playerdto.put("id", repoPlayer.findByUserName(authentication.getName()).getId());
        playerdto.put("name", repoPlayer.findByUserName(authentication.getName()).getusername());
        return playerdto;
    }

    private String playerAutentication(Authentication authentication) {
        return repoPlayer.findByUserName(authentication.getName()).getusername();
    }

    public Object getGameById(GamePlayer gameplayer){
        Map<String, Object> dto = new LinkedHashMap<>();

        dto.put("id", gameplayer.getId());
        dto.put("created", gameplayer.getGame().getCreationDate());
        dto.put("gamePlayers", this.gamePlayer(gameplayer.getGames()));
        dto.put("ships", gameplayer.getGamePlayerShipsDTO());
        dto.put("salvoes", gameplayer.getGames().getGameSalvosDTO());
        return dto;
    }

    private Player getAuthPlayer() {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        if (authentication1 == null || authentication1 instanceof AnonymousAuthenticationToken) {
            return null;
        } else {
            return repoPlayer.findByUserName(authentication1.getName());
        }
    }

}










