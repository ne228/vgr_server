package com.example.ais_ecc.munchkin.models.races;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.List;

public abstract class Races {
    protected String name;
    public abstract void accept(Player player);
    public abstract void discard(Player player);

    public abstract List<IAction> getActions(Player player);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
