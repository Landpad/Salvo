package com.accenture.Salvo;


import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private Date joinDate;
    public Date  getJoinDate() {
        return joinDate;
    }
    public GamePlayer(){ }

    public Long getId(){return id;}



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player players;
    public Player getPlayers() {
        return players;
    }
    public void setPlayer(Player players) {
        this.players = players;
    }
    public Player getPlayer(){
        return players;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game games;

    public Game getGames() {
        return games;
    }
    public void setGames(Game games) {
        this.games = games;
    }
    public Game getGame(){
        return games;
    }

    public GamePlayer(Game games, Player players, Date joinDate){
        this.games = games;
        this.players = players;
        this.joinDate = new Date();
    }
}























    /*@OneToMany(mappedBy = "players", cascade = CascadeType.ALL)
    private List<Player> player = new ArrayList<>();*/

    /*@OneToMany(mappedBy = "games", cascade = CascadeType.ALL)
    private List<Game> game = new ArrayList<>();*/










