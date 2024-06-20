package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.models.treasureCards.TreasureCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionHalfingSell extends IAction {
    Player player;
    TreasureCard sellCard;
    Move move;

    public ActionHalfingSell(Player player, TreasureCard card) {
        this.player = player;
        this.sellCard = card;
        path = "halfing_sell";
        name = "Халфинг: \"Продать шмотку\"";
    }

    public ActionHalfingSell() {
        path = "halfing_sell";
        name = "Халфинг: \"Продать шмотку\"";
    }


    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        player = context.getCurrentPlayer();
        move = context.getLastMove();

        if (!player.isRace(RaceList.HALFING))
            return false;

        if (move.getMoveCounter().getHalfingSell() == 0)
            return false;

        if (player == null || sellCard == null)
            return context.isAllNotFight();

        if (sellCard.getCost() <= 0)
            return false;

        var playerHaveTargetCard = player
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
        player.setGold(player.getGold() + (2 * sellCard.getCost()));
        move.getMoveCounter().setHalfingSell(move.getMoveCounter().getHalfingSell() - 1);
        return "Игрок " + player.getUser().getUsername() + " продал карту " + sellCard.getTitle() + " " + sellCard.getCost() + " голды. Использовал действией Хафлинга.";
    }
}
