package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.ListExtensions;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionTransferCard extends IAction {

    String nameRace;
    private Player whoTransferPlayer;
    private Player targetTransferPlayer;
    private Card targetCard;
    private Move move;
    private MunchkinContext context;

    public ActionTransferCard(Player whoTransferPlayer, Card targetCard, Player targetTransferPlayer) {
        this.targetCard = targetCard;
        this.whoTransferPlayer = whoTransferPlayer;
        this.targetTransferPlayer = targetTransferPlayer;

    }

    private ActionTransferCard() {
        this.path = "ActionTransferCard";
        this.name = "Transfer card to";
        this.title = "Transfer card to";
    }

    public static ActionTransferCard createAction() {
        return new ActionTransferCard();
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        var playerHaveTargetCard = whoTransferPlayer
                .getCards().stream()
                .anyMatch(card -> card.getId().equalsIgnoreCase(targetCard.getId()));
        if (!playerHaveTargetCard)
            return false;


        this.name = "Transfer card to" + targetTransferPlayer.getUser().getUsername();
        this.title = "Transfer card to " + targetTransferPlayer.getUser().getUsername();

        if (!context.isAllNotFight())
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {

        var extractCard = ListExtensions.extractById(whoTransferPlayer.getCards(), targetCard.getId());

        if (extractCard == null)
            throw new Exception("Card transfer error");

        targetTransferPlayer.getCards().add(extractCard);


        return "Player " + whoTransferPlayer.getUser().getUsername() + " transfer card " + targetCard.getTitle() + " to player " + targetTransferPlayer.getUser().getUsername();
    }
}
