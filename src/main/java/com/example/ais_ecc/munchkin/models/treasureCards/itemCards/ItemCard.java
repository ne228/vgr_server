package com.example.ais_ecc.munchkin.models.treasureCards.itemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.TreasureCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlayClasses;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlayRace;
import com.example.ais_ecc.munchkin.service.actions.card.ActionTakeOffClass;
import com.example.ais_ecc.munchkin.service.actions.card.ActionTakeOffRace;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemCard extends TreasureCard {

    public boolean bigSize = false;
    public int power = 0;

    public String itemType = "";

    public boolean itemCard = true;
    List<ISubscribe> subscribes;
    List<IAction> actionSubscribe;

    public ItemCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        actionSubscribe = new ArrayList<>();
        actionSubscribe.add(new ActionPlayRace(null, null));
        actionSubscribe.add(new ActionTakeOffRace(null, null));
        actionSubscribe.add(new ActionPlayClasses(null, null));
        actionSubscribe.add(new ActionTakeOffClass(null, null));
    }

    public abstract void accept(Player  player);

    public abstract void discard(Player player);

    public  boolean canPutItem(Player player){
        return true;
    }




    public boolean isBigSize() {
        return bigSize;
    }

    public void setBigSize(boolean bigSize) {
        this.bigSize = bigSize;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
