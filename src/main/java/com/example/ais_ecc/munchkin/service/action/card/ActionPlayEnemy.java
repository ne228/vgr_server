package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionPlayEnemy extends IAction {
    EnemyCard enemyCard;
    Fight fight;
    Move move;
    Player player;

    public ActionPlayEnemy(EnemyCard enemyCard) {
        this.enemyCard = enemyCard;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {

        context = munchkinContext;


        player = context.getCurrentPlayer();
        move = munchkinContext.getLastMove();
        if (move == null)
            return false;

        if (move.isFindTrouble())
            return true;

        return false;
    }

    @Override
    public String start() throws Exception {
        move.setFight(new Fight(player, context));
        move.getFight().getEnemyCards().add(enemyCard);
        move.endFindTroubles();
        return "Игрок " + player.getUser().getUsername() + " ищет неприятности и сраэается с " + enemyCard.getTitle();
    }
}
