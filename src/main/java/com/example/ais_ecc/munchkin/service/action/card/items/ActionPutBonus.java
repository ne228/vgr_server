package com.example.ais_ecc.munchkin.service.action.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.BonusItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionPutBonus extends IAction {
    private Player player;
    private BonusItemCard bonusItemCard;

    private MunchkinContext context;

    public ActionPutBonus(Player player, BonusItemCard bonusItemCard) {
        this.player = player;
        this.bonusItemCard = bonusItemCard;

        this.path = "Надеть";
        this.name = "Надеть";
        this.title = "Надеть";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (player.getHeadItemCard() != null)
            return false;

        if (!bonusItemCard.canPutItem(player))
            return false;

        if (!player.isHaveCard(bonusItemCard))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.getBonusItemCards().add(bonusItemCard);
        bonusItemCard.accept(player);
        context.discardCard(bonusItemCard.getId());

        return "Игрок " + player.getUser().getUsername() + " надел " + bonusItemCard.getTitle();
    }
}
