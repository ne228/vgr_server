package com.example.ais_ecc.munchkin.models.races;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.ArrayList;
import java.util.List;

public class ElfRace extends Races {

    public ElfRace() {
        this.name = "Elf";
    }
    @Override
    public void accept(Player player) {
        player.setDefBonusFlushing(player.getDefBonusFlushing() + 1);
    }

    @Override
    public void discard(Player player) {
        player.setDefBonusFlushing(player.getDefBonusFlushing() - 1);
    }

    @Override
    public List<IAction> getActions(Player player) {
        return new ArrayList<>();
    }


}
