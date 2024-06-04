package com.example.ais_ecc.munchkin.service.action.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionPutWeapon extends IAction {


    private Player player;
    private WeaponItemCard weaponItemCard;


    private MunchkinContext context;

    public ActionPutWeapon(Player player, WeaponItemCard weaponItemCard) {
        this.player = player;
        this.weaponItemCard = weaponItemCard;

        this.path = "Put";
        this.name = "Put";
        this.title = "Put";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;


        if (weaponItemCard.isTwoHands()) {
            // Оба слота доожны быть свободны
            if (player.getWeaponItemCard_1() != null || player.getWeaponItemCard_2() != null)
                return false;
        } else {
            // Хотя бы один слот должен быть свободным
            if (player.getWeaponItemCard_1() != null && player.getWeaponItemCard_2() != null)
                return false;


            // Оба слота доожны быть одноручными
            if (player.getWeaponItemCard_1() != null)
                if (player.getWeaponItemCard_1().isTwoHands())
                    return false;

            if (player.getWeaponItemCard_2() != null)
                if (player.getWeaponItemCard_2().isTwoHands())
                    return false;
        }

        if (!weaponItemCard.canPutItem(player))
            return false;

        if (!player.isHaveCard(weaponItemCard))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {

        if (weaponItemCard.isTwoHands()) {
            // Оба слота доожны быть свободны
            player.setWeaponItemCard_1(weaponItemCard);
        } else {
            // Хотя бы один слот должен быть свободным
            if (player.getWeaponItemCard_1() == null)
                player.setWeaponItemCard_1(weaponItemCard);
            else if (player.getWeaponItemCard_2() == null)
                player.setWeaponItemCard_2(weaponItemCard);
        }

        weaponItemCard.accept(player);
        context.discardCard(weaponItemCard.getId());

        return "Player " + player.getUser().getUsername() + " put on " + weaponItemCard.getTitle();   //TODO
    }
}
