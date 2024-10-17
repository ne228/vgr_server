package com.example.ais_ecc.munchkin.models;

import com.example.ais_ecc.munchkin.models.doorCards.enemyCards.EnemyCard;

public class Flushing {


    private Player player;
    private EnemyCard enemyCard;
    private boolean success;
    private boolean end = false;
    private int cubeNumber = 0;

    private int flushingSuccessNumber = 5;

    private boolean endRolling = false;

    public Flushing(Player player, EnemyCard enemyCard) {

        this.player = player;
        this.enemyCard = enemyCard;
    }

    public void endFlushing(boolean result) {
        end = true;
        success = result;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public EnemyCard getEnemyCard() {
        return enemyCard;
    }

    public void setEnemyCard(EnemyCard enemyCard) {
        this.enemyCard = enemyCard;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getCubeNumber() {
        return cubeNumber;
    }

    public void setCubeNumber(int cubeNumber) {
        this.cubeNumber = cubeNumber;
    }

    public boolean isEndRolling() {
        return endRolling;
    }

    public void setEndRolling(boolean endRolling) {
        this.endRolling = endRolling;
    }

    public int getFlushingSuccessNumber() {
        return flushingSuccessNumber;
    }

    public void setFlushingSuccessNumber(int flushingSuccessNumber) {
        this.flushingSuccessNumber = flushingSuccessNumber;
    }
}
