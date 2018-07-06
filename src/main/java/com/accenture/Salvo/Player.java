package com.accenture.Salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private long win;
    private long lose;
    private long tie;
    private long totalscore;

    @OneToMany(mappedBy="players", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<Score> scores;


    private String userName;
    private String password;


    public Player() { }

    public Player(String email, String password) {

        this.userName = email;
        this.password = password;
    }

    public String getusername() {
        return userName;
    }
    public void setusername (String userName) {
        this.userName = userName;
    }
    public Long getId () {return id; }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {

    }


    private long getWonGames() {
        long total = scores.stream().filter(score -> score.getScore() == 1).count();
        return total;
    }

    private double getLostGames() {
        long total = scores.stream().filter(score -> score.getScore() == 0).count();
        return total;
    }

    private long getTiedGames() {
        long total = scores.stream().filter(score -> score.getScore() == 0.5).count();
        return total;
    }

    public Map<String, Object> getPlayerDTO() {
        Map<String,Object>  playerDTO = new LinkedHashMap<>();
        playerDTO.put("id", this.id);
        playerDTO.put("email", this.userName);
        return playerDTO;
    }

    public Object getAllScoreDTO() {
        Map<String,Object> AllScoreDTO = new LinkedHashMap<>();
        AllScoreDTO.put("name", this.userName);
        AllScoreDTO.put("score", this.getScoreResumeDTO());
        return AllScoreDTO;
    }

    private Object getScoreResumeDTO() {
        Map<String,Object> scoreResume = new LinkedHashMap<>();
        long totalWon = this.getWonGames();
        double totalLost = this.getLostGames();
        double totalTie = this.getTiedGames();
        double totalTotal = scores.stream().filter(score ->
                score.getScore() != -1).mapToDouble(score -> score.getScore()).sum();
        scoreResume.put("total", totalTotal);
        scoreResume.put("won", totalWon);
        scoreResume.put("lost", totalLost);
        scoreResume.put("tied", totalTie);
        return scoreResume;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public List<Game> getGames() {
        return gamePlayers.stream().map(sub -> sub.getGames()).collect(toList());

    }



    public Object getPlayerScore(){
        return scores.stream().filter(s->s.getScore() !=1).collect(toList());
    }

    public Map<String, Object> getPlayer() {
        Map<String, Object> pdto = new LinkedHashMap<String, Object>();
        pdto.put("id", this.getId());
        pdto.put("email", this.getusername());
        return pdto;
    }

}




