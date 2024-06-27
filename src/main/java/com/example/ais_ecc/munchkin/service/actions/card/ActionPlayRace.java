package com.example.ais_ecc.munchkin.service.actions.card;

import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

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

        nameRace = raceCard.getTitle();
        this.name = "Play " + nameRace;
        this.title = "Play " + nameRace;

        if (!context.isAllNotFight())
            return false;

        if (player.isHalfBlood()) {
            if (player.getRaces().size() > 1)
                return false;
        } else if (player.getRaces().size() > 0)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.getRaces().add(raceCard);
        raceCard.accept(player);
        player.getCards().remove(raceCard);

        return "Player " + player.getUser().getUsername() + " is race + " + nameRace;   //TODO
    }
}
