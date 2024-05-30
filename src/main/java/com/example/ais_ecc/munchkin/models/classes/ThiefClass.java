package com.example.ais_ecc.munchkin.models.classes;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.ArrayList;
import java.util.List;

public class ThiefClass  extends Classes{
    public ThiefClass() {
        this.name = "Thief";
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
