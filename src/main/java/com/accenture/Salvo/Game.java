package com.accenture.Salvo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private Date creationDate ;
    public Game (){ }



    public Game (Date creationDate){
        this.creationDate = creationDate;
    }

    @OneToMany(mappedBy="games", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    public Date  getCreationDate()
    {
        return creationDate;
    }

    public Long getid(){
        return id;
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setGames(this);
        gamePlayers.add(gamePlayer);
    }

    @JsonIgnore
    public List<Player> getPlayers() {
        return gamePlayers.stream().map(sub -> sub.getPlayers()).collect(toList());




    }
}
