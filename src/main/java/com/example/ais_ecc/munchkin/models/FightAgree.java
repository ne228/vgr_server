package com.example.ais_ecc.munchkin.models;

import com.example.ais_ecc.munchkin.models.Player;

public class FightAgree {

    private final Player player;
    private boolean isAgree;

    public FightAgree(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setAgree(boolean agree) {
        isAgree = agree;
    }

    public boolean isAgree() {
        return isAgree;
    }
}
