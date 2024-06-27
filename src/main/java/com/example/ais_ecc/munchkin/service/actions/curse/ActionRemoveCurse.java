package com.example.ais_ecc.munchkin.service.actions.curse;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.CurseDoorCard;
import com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.bonusTreasureCardsImpl.wishingRing.WishingRing;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionRemoveCurse extends IAction {
    boolean typeConstCurse = false;


    Player player;
    CurseDoorCard curseDoorCard;

    ActionAcceptCurse actionAcceptCurse;

    WishingRing wishingRing;

    public ActionRemoveCurse(Player player, CurseDoorCard curseDoorCard, WishingRing wishingRing) {
        this.player = player;
        this.curseDoorCard = curseDoorCard;
        this.wishingRing = wishingRing;
        typeConstCurse = true;
        title = "Снять проклятие: " + curseDoorCard.getText();
        avoidRequired = true;
    }

    public ActionRemoveCurse(ActionAcceptCurse actionAcceptCurse, WishingRing wishingRing) {
        this.actionAcceptCurse = actionAcceptCurse;
        this.wishingRing = wishingRing;
        typeConstCurse = false;
        title = "Снять проклятие: " + actionAcceptCurse.getCurseDoorCard().getText();
        avoidRequired = true;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        if (typeConstCurse) {
            if (player.getCurses().stream().noneMatch(curse -> curse.getId().equalsIgnoreCase(curseDoorCard.getId())))
                return false;
        } else {
            var actions = context.getActionHandler().getRequiredActions();
            if (actions.stream().noneMatch(action -> action instanceof ActionAcceptCurse))
                return false;
        }
        return true;

    }

    @Override
    public String start() throws Exception {

        context.discardCard(wishingRing.getId());

        if (typeConstCurse) {
            player.getCurses().remove(curseDoorCard);
            context.getDiscardCards().add(curseDoorCard);
            curseDoorCard.curseRemove(player);

            return "Игрок " + player.getUser().getUsername() + " снял проклятие " + curseDoorCard.getText();
        } else {

            context.getActionHandler().getRequiredActions()
                    .remove(actionAcceptCurse);
            context.getActionHandler().updateAll();
            return "Игрок " + actionAcceptCurse.getPlayer().getUser().getUsername() + " снял проклятие " + actionAcceptCurse.getCurseDoorCard().getText();
        }
    }
}
