package com.accenture.Salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String userName;

    public Player() { }

    public Player(String first, String name, String last) {

        this.userName = first;
        this.firstName = name;
        this.lastName = last;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public Long getId () {return id; }

    public String toString() {
        return firstName + " " + lastName;
    }





    @OneToMany(mappedBy="players", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;


    public List<Game> getGames() {
        return gamePlayers.stream().map(sub -> sub.getGames()).collect(toList());

    }


    /*public Set<GamePlayer> getGames() {
        return gamePlayers;
    }*/

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        gamePlayers.add(gamePlayer);
    }
}




