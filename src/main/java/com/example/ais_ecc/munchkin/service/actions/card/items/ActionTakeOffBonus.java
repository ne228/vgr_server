package com.example.ais_ecc.munchkin.service.actions.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.BonusItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionTakeOffBonus extends IAction {
    private Player player;
    private BonusItemCard bonusItemCard;

    private MunchkinContext context;

    public ActionTakeOffBonus(Player player, BonusItemCard bonusItemCard) {
        this.player = player;
        this.bonusItemCard = bonusItemCard;

        this.path = "Снять";
        this.name = "Снять";
        this.title = "Снять";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (player.getPuttedBonusCard(bonusItemCard) == null)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {

        if (bonusItemCard == null)
            return "";
        player.getBonusItemCards().remove(bonusItemCard);
        bonusItemCard.discard(player);
        player.getCards().add(bonusItemCard);

        return "Игрок " + player.getUser().getUsername() + " снял  " + bonusItemCard.getTitle();   //TODO
    }

}
