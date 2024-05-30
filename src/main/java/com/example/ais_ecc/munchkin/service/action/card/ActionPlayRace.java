package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.Player;

import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionPlayRace extends IAction {

    String nameRace;
    private Player player;
    private RaceCard raceCard;
    private Move move;
    private MunchkinContext context;

    public ActionPlayRace(Player player, RaceCard raceCard) {
        this.player = player;
        this.raceCard = raceCard;
    }

    private ActionPlayRace() {
        this.path = "playRace";
        this.name = "Play race";
        this.title = "Play race";
    }

    public static ActionPlayRace createAction() {
        return new ActionPlayRace();
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        nameRace = raceCard.getRace().getName();
        this.name = "Play " + nameRace;
        this.title = "Play " + nameRace;

        if (!context.isAllNotFight())
            return false;

        if (player.getRaces().size() > 0)
            return false;

        var player = context.getCurrentPlayer();
        var playerHaveTargetCard = player
                .getCards().stream()
                .anyMatch(card -> card.getId().equalsIgnoreCase(raceCard.getId()));
        if (!playerHaveTargetCard)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.getRaces().add(raceCard.getRace());
        raceCard.getRace().accept(player);
        context.discardCard(raceCard.getId());

        return "Player " + player.getUser().getUsername() + " is race + " + nameRace;   //TODO
    }
}
