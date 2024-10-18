package com.example.ais_ecc.munchkin.service.actions.curse.curseImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.List;

public class ActionDropAllRaces extends IAction {

    private final Player player;

    public ActionDropAllRaces(Player player) {
        this.player = player;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return true;
    }

    @Override
    public String start() throws Exception {
        List<RaceCard> races = (List<RaceCard>) List.copyOf(player.getRaces());
        for (RaceCard race : races) {
            player.getRaces().remove(race);
            race.discard(player);
            player.getCards().add(race);
            context.discardCard(race.getId());
        }
        return "Игрок " + player.getUser().getUsername() + " сбросил все расы";
    }
}
