package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Flushing;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionAddEnemyCard;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlayEnemy;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class EnemyCard extends DoorCard {

    public int level;
    public int rewardLevel;
    public int rewardTreasure;
    public int levelChaise = -1;
    public boolean enemyCard = true;
    public boolean unDead = false;
    protected int defaultFlushValue = 4;
    protected String obscenityText;

    public EnemyCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    public abstract void obscenity(Fight fight, Player player) throws Exception;

    public int getTotalPower(Fight fight) throws Exception {

        return level;
    }

    public boolean canChaise(Player player) {
        return true;
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {
        var actionAddEnemyCard = new ActionAddEnemyCard(this);
        if (actionAddEnemyCard.canAmI(getMunchkinContext()))
            return actionAddEnemyCard;

        var actionPlayEnemy = new ActionPlayEnemy(this);
        if (actionPlayEnemy.canAmI(getMunchkinContext())) {
            return actionPlayEnemy;
        }
        return null;
    }

    @Override
    @JsonIgnore
    public List<CardAction> getActions() throws Exception {
        var actions = new ArrayList<CardAction>();
        actions.addAll(super.getActions());

        var request = new PlayCardRequest(this.getId(), "null", false, "null");

        var actionAddEnemyCard = new ActionAddEnemyCard(this);
        if (actionAddEnemyCard.canAmI(getMunchkinContext())) {
            var cardAction = new CardAction(request.toEndpointPath("play_card", getMunchkinContext().getId()), "Подкинуть монстра");
            actions.add(cardAction);
        }

        var actionPlayEnemy = new ActionPlayEnemy(this);
        if (actionPlayEnemy.canAmI(getMunchkinContext())) {
            var cardAction = new CardAction(request.toEndpointPath("play_card", getMunchkinContext().getId()), "Сыграть монстра");
            actions.add(cardAction);
        }

        return actions;
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

    public void flushing(Flushing flushing) {
    }
}
