package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.enemyCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.service.ListExtensions;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.ArrayList;

public class ActionWizardPacification extends IAction {

    EnemyCard enemyCard;
    Player player;

    public ActionWizardPacification(EnemyCard enemyCard) {
        this.enemyCard = enemyCard;
        path = "wizard_pacification";
        name = "Волебник: \"Заклинание изгнание\"";
    }

    public ActionWizardPacification() {
        path = "wizard_pacification";
        name = "Волебник: \"Заклинание изгнание\"";
    }

    Fight fight;

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        if (!context.isGameStarted)
            return false;

        player = context.getCurrentPlayer();
        if (!player.isClass(ClassList.WIZARD))
            return false;

        fight = context.getFight();
        if (fight == null)
            return false;

        if (fight.isEnd())
            return false;

        if (fight.isLoss())
            return false;

        if (player.getCards().size() < 3)
            return false;

        if (enemyCard != null)
            if (fight.getEnemyCards().stream().noneMatch(card -> card.getId().equalsIgnoreCase(enemyCard.getId())))
                return false;

        if (fight.getEnemyCards().size() == 0)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        fight.getEnemyCards().remove(enemyCard);
        fight.getPlayer().getCards().add(enemyCard);

        var cards = new ArrayList<Card>(player.getCards());
        for (var card : cards) {
            context.discardCard(card.getId());
        }

        for (int treasureI = 0; treasureI < enemyCard.getRewardTreasure(); treasureI++) {
            var treasureCard = ListExtensions.extractRandom(context.getTreasureCards());
            fight.getPlayer().getCards().add(treasureCard);
        }

        return "Игрок " + player.getUser().getUsername() + " использовал Изгнание." + enemyCard.getTitle() + " изгнан из сражения";
    }
}
