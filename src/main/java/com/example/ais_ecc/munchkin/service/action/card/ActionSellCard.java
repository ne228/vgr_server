package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.TreasureCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionSellCard extends IAction {

    private TreasureCard sellCard;

    private Player whoSellCard;

    private MunchkinContext context;

    public ActionSellCard(TreasureCard sellCard, Player whoSellCard) {
        this.sellCard = sellCard;
        this.whoSellCard = whoSellCard;
        this.path = "Sell card";
        this.name = "Sell card";
        this.title = "Sell card";

    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (sellCard.getCost() <= 0)
            return false;

        var playerHaveTargetCard = whoSellCard
                .getCards().stream()
                .anyMatch(card -> card.getId().equalsIgnoreCase(sellCard.getId()));
        if (!playerHaveTargetCard)
            return false;

        if (!context.isAllNotFight())
            return false;



        return true;
    }

    @Override
    public String start() throws Exception {
        context.discardCard(sellCard.getId());
        whoSellCard.setGold(whoSellCard.getGold() + sellCard.cost);
        return "Player " + whoSellCard.getUser().getUsername() + " sold card " + sellCard.getTitle() + " " + sellCard.getCost() + " gold";
    }
}
