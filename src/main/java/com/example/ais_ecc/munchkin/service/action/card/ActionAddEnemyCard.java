package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.doorCards.WalkingMonsterCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.stream.Collectors;

public class ActionAddEnemyCard extends IAction {
    EnemyCard enemyCard;
    Fight fight;
    Move move;
    Player player;

    public ActionAddEnemyCard(EnemyCard enemyCard) {
        this.enemyCard = enemyCard;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {

        context = munchkinContext;

        player = context.getCurrentPlayer();
        move = munchkinContext.getLastMove();
        if (move == null)
            return false;


        fight = move.getFight();
        if (fight == null)
            return false;


        if (fight.isEnd())
            return false;

        if (fight.isAgree())
            return false;


        var playerHaveTargetCard = player
                .getCards().stream()
                .anyMatch(card -> card.getId().equalsIgnoreCase(enemyCard.getId()));
        if (!playerHaveTargetCard)
            return false;

        if (fight.enemyCards.stream().anyMatch(card -> card.isUnDead()) && enemyCard.isUnDead())
            return true;

        if (fight.getDoorCards().stream().anyMatch(card -> card.getClass() == WalkingMonsterCard.class))
            return true;

        return false;
    }

    @Override
    public String start() throws Exception {
        if (fight.enemyCards.stream().anyMatch(card -> card.isUnDead()) && enemyCard.isUnDead()) {
            fight.getEnemyCards().add(enemyCard);
            context.discardCard(enemyCard.getId());
            return "Игрок " + player.getUser().getUsername() + " подкинул монстра Андеда  " + enemyCard.getTitle();
        }
        var cards = fight.getDoorCards().stream().filter(card -> card.getClass() == WalkingMonsterCard.class).collect(Collectors.toList());
        if (cards.size() != 0) {
            fight.getEnemyCards().add(enemyCard);
            context.discardCard(enemyCard.getId());
            context.discardCard(cards.get(0).getId());
            return "Игрок " + player.getUser().getUsername() + " подкинул монстра (бродячая тварь)  " + enemyCard.getTitle();
        }

        return null;
    }
}
