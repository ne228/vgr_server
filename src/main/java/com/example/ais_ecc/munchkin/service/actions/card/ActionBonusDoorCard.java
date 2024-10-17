package com.example.ais_ecc.munchkin.service.actions.card;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.bonusDoorCards.BonusDoorCard;
import com.example.ais_ecc.munchkin.models.doorCards.enemyCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionBonusDoorCard extends IAction {


    private BonusDoorCard bonusDoorCard;
    private EnemyCard enemyCard;
    private Player player;
    private Move move;
    private Fight fight;
    private MunchkinContext context;

    public ActionBonusDoorCard(BonusDoorCard bonusDoorCard, EnemyCard enemyCard, Player player) {
        this.player = player;
        this.bonusDoorCard = bonusDoorCard;
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
                .anyMatch(card -> card.getId().equalsIgnoreCase(bonusDoorCard.getId()));
        if (!playerHaveTargetCard)
            return false;


        return true;
    }

    @Override
    public String start() throws Exception {

        enemyCard.setLevel(enemyCard.getLevel() + bonusDoorCard.bonusPower);
        enemyCard.setRewardTreasure(enemyCard.getRewardTreasure() + bonusDoorCard.bonusTreasure);

        context.discardCard(bonusDoorCard.getId());
        return "Player " + player.getUser().getUsername() + " bonus monster + " + enemyCard.getTitle();
    }
}
