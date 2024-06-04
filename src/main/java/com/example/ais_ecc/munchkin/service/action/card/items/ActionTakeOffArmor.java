package com.example.ais_ecc.munchkin.service.action.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ArmorItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionTakeOffArmor extends IAction {
    private Player player;
    private ArmorItemCard armorItemCard;

    private MunchkinContext context;

    public ActionTakeOffArmor(Player player, ArmorItemCard armorItemCard) {
        this.player = player;
        this.armorItemCard = armorItemCard;

        this.path = "Take off";
        this.name = "Take off";
        this.title = "Take off";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (player.getArmorItemCard() == null)
            return false;

        if (!player.getArmorItemCard().getId().equalsIgnoreCase(armorItemCard.getId()))
            return false;

        if (!player.isHaveCard(armorItemCard))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.setArmorItemCard(null);
        armorItemCard.discard(player);
        player.getCards().add(armorItemCard);

        return "Player " + player.getUser().getUsername() + " take off " + armorItemCard.getTitle();   //TODO
    }
}
