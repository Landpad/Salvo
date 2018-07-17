package com.accenture.Salvo;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String tie;
    private double score;
    private Date finishdate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerId")
    private Player player;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameId")
    private Game game;

    public Score() {
    }

    ;

    public Score(double score, Player player, Game game) {
        this.player = player;
        this.game = game;
        this.score = score;
        if (this.score != -1) {
            this.finishdate = game.getCreationDate();
            this.finishdate = Date.from(finishdate.toInstant().plusSeconds(1800));
            //this.score = score;
        } else {
            this.finishdate = null;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTie() {
        return tie;
    }

    public void setTie(String tie) {
        this.tie = tie;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Map<String,Object> getGamePlayerScore() {
        Map<String,Object> scoreList = new LinkedHashMap<>();
        scoreList.put("score", this.getScore());
        return scoreList;
    }

    public Map<String,Object> getPlayerScore() {
        Map<String,Object> scoreList = new LinkedHashMap<>();
        scoreList.put("playerID", this.getId());
        scoreList.put("score", this.score);
        scoreList.put("finish date", this.finishdate);
        return scoreList;
    }


}


