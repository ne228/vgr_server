package com.example.ais_ecc.munchkin.models.races;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.ArrayList;
import java.util.List;

public class DwarfRace extends Races {

    public DwarfRace() {
        this.name = "Dwarf";
        this.race = RaceList.DWARF;
    }
    int canUseBigСlothesCount = 10;
    int canHandCardCount = 6;
    @Override
    public void accept(Player player) {
        player.setCanUseBigСlothesCount(player.getCanUseBigСlothesCount() + canUseBigСlothesCount);
        player.setCanHandCardCount(player.getCanHandCardCount() + canHandCardCount);
    }

    @Override
    public void discard(Player player) {
        player.setCanUseBigСlothesCount(player.getCanUseBigСlothesCount() - canUseBigСlothesCount);
        player.setCanHandCardCount(player.getCanHandCardCount() - canHandCardCount);
    }

    @Override
    public List<IAction> getActions(Player player) {
        return new ArrayList<>();
    }

}
