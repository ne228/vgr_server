package com.example.ais_ecc.munchkin.service.actions.curse;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.CurseDoorCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;

import java.util.UUID;

public class ActionAcceptCurse extends RequiredAction {

    private final Player player;
    private final CurseDoorCard curseDoorCard;

    private IAction nextAction;


    public ActionAcceptCurse(Player player, CurseDoorCard curseDoorCard, MunchkinContext context) {
        this.player = player;
        this.curseDoorCard = curseDoorCard;
        setScopeId(UUID.randomUUID().toString());
        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Принять проклятие :(";
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();

        if (!currentPlayer.getId().equalsIgnoreCase(player.getId()))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        curseDoorCard.curseDo(player);
//        context.discardCard(curseDoorCard.getId());
        return "Игрок " + player.getUser().getUsername() + " проклят. " + curseDoorCard.getText();
    }

    public Player getPlayer() {
        return player;
    }

    public CurseDoorCard getCurseDoorCard() {
        return curseDoorCard;
    }

    public IAction getNextAction() {
        return nextAction;
    }
}
