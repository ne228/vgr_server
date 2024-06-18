package com.example.ais_ecc.munchkin.service.actions.fight;

import com.example.ais_ecc.munchkin.models.*;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.ListExtensions;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.ActionNextMove;
import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ActionFightEnd extends IAction {


    private Player player;
    private Move move;

    private Fight fight;
    private MunchkinContext context;

    public ActionFightEnd(Player player) {
        this.player = player;
    }

    private ActionFightEnd(MunchkinContext context) {
        this.path = "fight_end/" + context.getId(); //TODO
        this.name = "Fight End";
        this.title = "Fight End TODO";
    }

    public static ActionFightEnd createAction(MunchkinContext context) {
        return new ActionFightEnd(context);
    }

    public void setPaths(MunchkinContext context) throws Exception {

    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        player = munchkinContext.getCurrentPlayer();
        if (!context.isGameStarted)
            return false;

        move = context.getLastMove();
        if (move == null)
            return false;

        fight = move.getFight();
        if (fight == null)
            return false;

        if (fight.isEnd())
            return false;

        if (fight.isLoss())
            return false;

        for (var fightAgree : fight.getFightAgrees()) {
            if (!fightAgree.isAgree())
                return false;
        }

        if (!fight.getPlayer().getId().equalsIgnoreCase(player.getId()))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        int playerPower = fight.getPlayersPower();
        int enemyPower = fight.getEnemiesPower();
        fight.setEnd(true);
        List<OrderFight> fightOrders = fight.getFightOrders();
        for (int i = 0; i < fightOrders.size(); i++) {
            OrderFight order = fightOrders.get(i);
            if (!order.isTrust()) {
                fight.getFightOrders().remove(order);
            }
        }


        if (playerPower > enemyPower) {
            int maxLvlReward = fight.getEnemyCards().stream()
                    .mapToInt(EnemyCard::getRewardLevel)
                    .max()
                    .orElseThrow(NoSuchElementException::new);


            int totalTreasureReward = fight.getEnemyCards().stream()
                    .mapToInt(EnemyCard::getRewardTreasure)
                    .sum();

            fight.getPlayer().lvlUp(maxLvlReward);

            for (var orderFight : fight.getFightOrders()) {
                for (int rewardI = 0; rewardI < orderFight.treasureCount; rewardI++) {
                    if (totalTreasureReward == 0)
                        break;
                    totalTreasureReward--;
                    var cardTreasure = ListExtensions.extractRandom(context.getTreasureCards());
                    orderFight.getPlayer().getCards().add(cardTreasure);
                }
            }
            for (int rewardI = 0; rewardI < totalTreasureReward; rewardI++) {
                var cardTreasure = ListExtensions.extractRandom(context.getTreasureCards());
                fight.getPlayer().getCards().add(cardTreasure);
            }

            move.setEnd(true);
            context.getActionHandler().doAction(new ActionNextMove(context));
            return "Игрок " + fight.getPlayer().getUser().getUsername() + " выиграл сражение!";

            // Заебисся пахнет пися
        } else {
            fight.setLoss(true);
            fight.setFlushings(new ArrayList<>());
            var playersFlushing = new ArrayList<Player>();
            playersFlushing.add(fight.getPlayer());
            for (var order : fight.getFightOrders())
                playersFlushing.add(order.getPlayer());

            for (var enemyCard : fight.getEnemyCards())
                for (var player : playersFlushing) {
                    if (player.getLvl() < enemyCard.getLevelChaise())
                        continue;
                    if (!enemyCard.canChaise(player))
                        continue;
                    fight.getFlushings().add(new Flushing(player, enemyCard));
                }
            if (fight.getFlushings().size() == 0) {
                var nextMove = new ActionNextMove(context);
                context.getActionHandler().doAction(nextMove);
            }
            fight.setLoss(true);
            return "Player " + fight.getPlayer().getUser().getUsername() + " loss the fight!";
        }
    }

}
