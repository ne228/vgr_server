package com.example.ais_ecc.munchkin.models;


import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Move {

    // Если true то сейчас этот ход возможен
    public boolean isKickDoor = true;
    public boolean isFindTrouble = false;
    public boolean isCleaningNooks = false;
    public boolean isFromBounty = false;
    public boolean isEnd;
    public Fight fight;

    Player player;
    @JsonIgnore
    MunchkinContext context;

    public Move(Player player, MunchkinContext context) {
        this.context = context;
        this.player = player;
    }

    public void endKickDoor() {
        isKickDoor = false;
        isFindTrouble = true;
        isCleaningNooks = true;
    }

    public void endFindTroubles() {
        isFindTrouble = false;
        isCleaningNooks = false;
        isFromBounty = true;
    }

    public void endCleaningNooks() {
        isFindTrouble = false;
        isCleaningNooks = false;
        isFromBounty = true;
    }

    public void endFromBounty() {
        isFromBounty = false;
        isEnd = true;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isKickDoor() {
        return isKickDoor;
    }

    public void setKickDoor(boolean kickDoor) {
        isKickDoor = kickDoor;
    }

    public boolean isFindTrouble() {
        return isFindTrouble;
    }

    public void setFindTrouble(boolean findTrouble) {
        isFindTrouble = findTrouble;
    }

    public boolean isCleaningNooks() {
        return isCleaningNooks;
    }

    public void setCleaningNooks(boolean cleaningNooks) {
        isCleaningNooks = cleaningNooks;
    }

    public boolean isFromBounty() {
        return isFromBounty;
    }

    public void setFromBounty(boolean fromBounty) {
        isFromBounty = fromBounty;
    }

    public Fight getFight() {
        return fight;
    }

    public void setFight(Fight fight) {
        this.fight = fight;
    }

    public MunchkinContext getContext() {
        return context;
    }

    public void setContext(MunchkinContext context) {
        this.context = context;
    }
}
