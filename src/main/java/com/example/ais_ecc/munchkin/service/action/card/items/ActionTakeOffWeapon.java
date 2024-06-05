package com.example.ais_ecc.munchkin.service.action.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionTakeOffWeapon extends IAction {


    private Player player;
    private WeaponItemCard weaponItemCard;


    private MunchkinContext context;

    public ActionTakeOffWeapon(Player player, WeaponItemCard weaponItemCard) {
        this.player = player;
        this.weaponItemCard = weaponItemCard;

        this.path = "Take off";
        this.name = "Take off";
        this.title = "Take off";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {

        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (!player.isHaveCard(weaponItemCard))
            return false;

        if (player.getWeaponItemCard_1() != null) {
            if (player.getWeaponItemCard_1().getId().equalsIgnoreCase(weaponItemCard.getId()))
                return true;
        }
        if (player.getWeaponItemCard_2() != null) {
            if (player.getWeaponItemCard_2().getId().equalsIgnoreCase(weaponItemCard.getId()))
                return true;
        }



        return false;
    }

    @Override
    public String start() throws Exception {
        if (weaponItemCard == null)
            return "";

        if (player.getWeaponItemCard_1() != null)
            if (player.getWeaponItemCard_1().getId().equalsIgnoreCase(weaponItemCard.getId())) {
                player.setWeaponItemCard_1(null);
                weaponItemCard.discard(player);
                player.getCards().add(weaponItemCard);
            }

        if (player.getWeaponItemCard_2() != null)
            if (player.getWeaponItemCard_2().getId().equalsIgnoreCase(weaponItemCard.getId())) {
                player.setWeaponItemCard_2(null);
                weaponItemCard.discard(player);
                player.getCards().add(weaponItemCard);
            }

        weaponItemCard.discard(player);
        return "Player " + player.getUser().getUsername() + " take off " + weaponItemCard.getTitle();   //TODO
    }
}
