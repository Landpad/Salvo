package com.accenture.Salvo;

import com.accenture.Salvo.Game;
import com.accenture.Salvo.GamePlayer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int turn;
    private String Salvo;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayerID")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name = "SalvoLocations")
    private List<String> salvolocations;
    public Salvo(){}

    public Salvo (GamePlayer gamePlayer,int turn, List<String> salvolocations ){
        this.gamePlayer = gamePlayer;
        this.turn = turn;
        this.salvolocations = salvolocations;

    }

    public Long getId() { return id; }


    public List<String> getSalvolocations() {
        return salvolocations;
    }

    public void setSalvolocations(List<String> salvolocations) {
        this.salvolocations = salvolocations;
    }

    public int getTurn() {
        return turn;
    }

    public String getSalvo() {
        return Salvo;
    }

    public void setSalvo(String salvo) {
        Salvo = salvo;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void setGameplayers(GamePlayer ships) {
        this.gamePlayer = ships;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }



   public Map<String,Object> getSalvoDTO() {
        Map<String,Object> salvoList = new LinkedHashMap<>();
        salvoList.put("turn",this.turn);
        salvoList.put("player",gamePlayer.getPlayer().getId());
        salvoList.put("locations", this.salvolocations);


        return salvoList;
    }









}
