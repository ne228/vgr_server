package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionTakeOffRace extends IAction {
    String nameRace;
    private Player player;
    private RaceCard raceCard;
    private Move move;
    private MunchkinContext context;

    public ActionTakeOffRace(Player player, RaceCard raceCard) {
        this.player = player;
        this.raceCard = raceCard;
    }


    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        nameRace = raceCard.getTitle();
        this.name = "Сбросить  " + nameRace;
        this.title = "Сбросить  " + nameRace;

        if (!context.isAllNotFight())
            return false;


        if (!player.isRace(raceCard.getRace()))
            return false;

        if (player.getRaces().stream().noneMatch(card -> card.getId().equalsIgnoreCase(raceCard.getId())))
            return false;


        return true;
    }

    @Override
    public String start() throws Exception {
        player.getRaces().remove(raceCard);
        raceCard.discard(player);
        player.getCards().add(raceCard);


        return "Игрок " + player.getUser().getUsername() + "сбросил расу  " + nameRace;   //TODO
    }
}
