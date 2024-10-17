package com.example.ais_ecc.munchkin.models;


import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.models.doorCards.enemyCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.treasureCards.TreasureCard;
import com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.BonusTreasureCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;


public class Fight {

    @JsonIgnore
    private final MunchkinContext context;

    @JsonIgnore
    public Player player;

    private FightCounter fightCounter;

    public List<EnemyCard> enemyCards;
    private List<TreasureCard> helpTreasureCards;

    private List<DoorCard> doorCards;
    private List<TreasureCard> harmTreasureCards;
    private List<FightAgree> fightAgrees;
    private List<OrderFight> fightOrders;
    private int bonusPlayerPower = 0;
    private boolean isEnd;
    private List<Flushing> flushings;


    private boolean isLoss;


    public Fight(Player player, MunchkinContext context) {
        this.context = context;
        fightAgrees = new ArrayList<>();
        doorCards = new ArrayList<>();
        fightCounter = new FightCounter();
        for (var player_game : context.getPlayers())
            fightAgrees.add(new FightAgree(player_game));

        setEnemyCards(new ArrayList<>());
        setHelpTreasureCards(new ArrayList<>());
        setHarmTreasureCards(new ArrayList<>());
        setFightOrders(new ArrayList<>());
        setPlayer(player);
    }

    public void setAgree(Player player, boolean agree) {
        for (var fightAgree : fightAgrees) {
            if (player.getId() == fightAgree.getPlayer().getId()) {
                fightAgree.setAgree(agree);
                return;
            }
        }
    }

    public boolean getAgree(Player player) {
        for (var fightAgree : fightAgrees) {
            if (player.getId() == fightAgree.getPlayer().getId()) {
                return fightAgree.isAgree();
            }
        }
        return false;
    }

    public boolean isAgree() {
        for (var fightAgree : getFightAgrees()) {
            if (!fightAgree.isAgree())
                return false;
        }
        return true;
    }

    public boolean getAgree() {
        var player = context.getCurrentPlayer();
        for (var fightAgree : fightAgrees) {
            if (player.getId() == fightAgree.getPlayer().getId()) {
                return fightAgree.isAgree();
            }
        }
        return false;
    }

    public int getPlayersPower() {
        var power = player.getTotalPower();
        power += bonusPlayerPower;

        for (var fightOrder : getFightOrders()) {
            if (fightOrder.isTrust()) {
                power += fightOrder.getPlayer().getTotalPower();
            }
        }

        for (var helpCard : helpTreasureCards) {
            if (helpCard instanceof BonusTreasureCard)
                power += ((BonusTreasureCard) helpCard).getBonus();
        }
        return power;
    }

    public int getEnemiesPower() throws Exception {
        int power = 0;
        for (var enemyCard : enemyCards) {
            power += enemyCard.getTotalPower(this);
            // TODO нужно добавить както услвоия +3 против эльфов и тд и тп для других игроков
        }
        for (var harmCard : harmTreasureCards) {
            if (harmCard instanceof BonusTreasureCard)
                power += ((BonusTreasureCard) harmCard).getBonus();
        }
        return power;
    }

    public void addBonusPlayerPower(int addPower) {
        bonusPlayerPower += addPower;
    }


    public ArrayList<Player> getFightPlayers() {
        var res = new ArrayList<Player>();
        res.add(getPlayer());
        for (var fightOrder : getFightOrders()) {
            if (fightOrder.isTrust())
                res.add(fightOrder.getPlayer());
        }
        return res;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<EnemyCard> getEnemyCards() {
        return enemyCards;
    }

    public void setEnemyCards(List<EnemyCard> enemyCards) {
        this.enemyCards = enemyCards;
    }

    public List<TreasureCard> getHelpTreasureCards() {
        return helpTreasureCards;
    }

    public void setHelpTreasureCards(List<TreasureCard> helpTreasureCards) {
        this.helpTreasureCards = helpTreasureCards;
    }

    public List<TreasureCard> getHarmTreasureCards() {
        return harmTreasureCards;
    }

    public void setHarmTreasureCards(List<TreasureCard> harmTreasureCards) {
        this.harmTreasureCards = harmTreasureCards;
    }

    public List<OrderFight> getFightOrders() {
        return fightOrders;
    }

    public void setFightOrders(List<OrderFight> orders) {
        this.fightOrders = orders;
    }

    public boolean isEnd() {
        if (flushings != null)
            if (flushings.size() > 0)
                return false;

        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    @JsonIgnore
    public MunchkinContext getContext() {
        return context;
    }

    public List<FightAgree> getFightAgrees() {
        return fightAgrees;
    }

    public void setFightAgrees(List<FightAgree> fightAgrees) {
        this.fightAgrees = fightAgrees;
    }


    public List<Flushing> getFlushings() {
        return flushings;
    }

    public void setFlushings(List<Flushing> flushings) {
        this.flushings = flushings;
    }

    public boolean isLoss() {
        return isLoss;
    }

    public void setLoss(boolean loss) {
        isLoss = loss;
    }

    public List<DoorCard> getDoorCards() {
        return doorCards;
    }

    public void setDoorCards(List<DoorCard> doorCards) {
        this.doorCards = doorCards;
    }

    public int getBonusPlayerPower() {
        return bonusPlayerPower;
    }

    public void setBonusPlayerPower(int bonusPlayerPower) {
        this.bonusPlayerPower = bonusPlayerPower;
    }

    public FightCounter getFightCounter() {
        return fightCounter;
    }

    public void setFightCounter(FightCounter fightCounter) {
        this.fightCounter = fightCounter;
    }
}
