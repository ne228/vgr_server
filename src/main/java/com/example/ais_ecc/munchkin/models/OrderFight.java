package com.example.ais_ecc.munchkin.models;

import java.util.UUID;

public class OrderFight {

    public String id;

    public Player player;
    public int treasureCount;

    public boolean trust;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getTreasureCount() {
        return treasureCount;
    }

    public void setTreasureCount(int treasureCount) {
        this.treasureCount = treasureCount;
    }

    public boolean isTrust() {
        return trust;
    }

    public void setTrust(boolean trust) {
        this.trust = trust;
    }

    public String getId() {
        if (id == null)
            setId(UUID.randomUUID().toString());
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
