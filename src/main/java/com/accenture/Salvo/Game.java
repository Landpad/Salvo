package com.accenture.Salvo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private Date creationDate ;
    private Date joindate;
    @OneToMany(mappedBy="games", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<Score> scores;

    public Game (){ }
    public Game (Date creationDate){
        this.creationDate = creationDate;
    }
    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }
    public Date  getCreationDate()
    {
        return creationDate;
    }
    public Long getId(){return id;}

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    @JsonIgnore
    public List<Player> getPlayers() {
        return gamePlayers.stream().map(sub -> sub.getPlayers()).collect(toList());
    }


    public Object getGameSalvosDTO(){
        return gamePlayers.stream().flatMap(g->g.getSalvos().stream().map(s ->s.getSalvoDTO())).collect(toList());
    }


    public Object getGamePlayerScore(){
        return getPlayers().stream().flatMap(g->g.scores.stream().map(s->s.getPlayerScore())).collect(toList());
    }



    public Map<String, Object> getDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("created", this.getCreationDate());
        dto.put("gamePlayers", gamePlayers.stream().map(s->s.gamePlayer()).collect(toList()));
        dto.put("scores", this.getGamePlayerScore());

        return dto;
    }



}
