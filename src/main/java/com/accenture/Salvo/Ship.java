package com.accenture.Salvo;

import com.accenture.Salvo.Game;
import com.accenture.Salvo.GamePlayer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String ship;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayerShips")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name = "locations")
    private List<String> locations;

    private Ship() {
    }

    public Ship(String ship, GamePlayer gamePlayer, List<String> locations) {
        this.ship = ship;
        this.gamePlayer = gamePlayer;
        this.locations = locations;
    }

    public Long getId() {
        return id;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    private List<String> getLocations() {
        return locations;
    }

    public void setGameplayers(GamePlayer ships) {
        this.gamePlayer = ships;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public Map<String, Object> getShipDTO() {
        Map<String, Object> shipList = new LinkedHashMap<>();
        shipList.put("type", this.ship);
        shipList.put("locations", this.locations);
        return shipList;
    }
}




