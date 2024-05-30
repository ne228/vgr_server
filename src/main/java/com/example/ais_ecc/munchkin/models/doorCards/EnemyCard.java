package com.example.ais_ecc.munchkin.models.doorCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.List;

public abstract class EnemyCard extends DoorCard {

    public int level;
    protected int defaultFlushValue = 4;

    public int rewardLevel;
    public int rewardTreasure;

    public int levelChaise = -1;

    public boolean enemyCard = true;
    protected String obscenityText;

    public boolean unDead = false;

    public EnemyCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    public abstract void obscenity(Player player);

    public int getTotalPower(){
        return level;
    }

    public abstract boolean canChaise(Player player);

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) {
        return null;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        return super.getActions();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDefaultFlushValue() {
        return defaultFlushValue;
    }

    public void setDefaultFlushValue(int defaultFlushValue) {
        this.defaultFlushValue = defaultFlushValue;
    }

    public int getPower(Player player) {
        return level;
    }


    public int getRewardLevel() {
        return rewardLevel;
    }

    public void setRewardLevel(int rewardLevel) {
        this.rewardLevel = rewardLevel;
    }

    public int getRewardTreasure() {
        return rewardTreasure;
    }

    public void setRewardTreasure(int rewardTreasure) {
        this.rewardTreasure = rewardTreasure;
    }

    public String getObscenityText() {
        return obscenityText;
    }

    public void setObscenityText(String obscenityText) {
        this.obscenityText = obscenityText;
    }

    public boolean isEnemyCard() {
        return enemyCard;
    }

    public void setEnemyCard(boolean enemyCard) {
        this.enemyCard = enemyCard;
    }

    public boolean isUnDead() {
        return unDead;
    }

    public void setUnDead(boolean unDead) {
        this.unDead = unDead;
    }

    public int getLevelChaise() {
        return levelChaise;
    }

    public void setLevelChaise(int levelChaise) {
        this.levelChaise = levelChaise;
    }
}
