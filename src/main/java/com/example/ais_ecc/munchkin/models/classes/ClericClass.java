package com.example.ais_ecc.munchkin.models.classes;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.fight.ActionConfirmFightOrder;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

import java.util.ArrayList;
import java.util.List;

public class ClericClass extends Classes {

    int canUseBigСlothesCount = 10;
    int canHandCardCount = 6;
    public ClericClass() {
        this.name = "Cleric";
    }

    @Override
    public void accept(Player player) {


    }

    @Override
    public void discard(Player player) {

    }

    @Override
    public List<IAction> getActions(Player player) {
        return new ArrayList<>();
    }
}
