package com.example.ais_ecc.munchkin.service.action.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ArmorItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionPutArmor extends IAction {


    private Player player;
    private ArmorItemCard armorItemCard;

    private MunchkinContext context;

    public ActionPutArmor(Player player, ArmorItemCard armorItemCard) {
        this.player = player;
        this.armorItemCard = armorItemCard;

        this.path = "Put";
        this.name = "Put";
        this.title = "Put";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (player.getArmorItemCard() != null)
            return false;

        if (!armorItemCard.canPutItem(player))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.setArmorItemCard(armorItemCard);
        armorItemCard.accept(player);
        context.discardCard(armorItemCard.getId());

        return "Player " + player.getUser().getUsername() + " put on " + armorItemCard.getTitle() ;   //TODO
    }
}
