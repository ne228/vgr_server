package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.fight.ActionDiscardFightOrders;

public class Gazebo extends EnemyCard {

    boolean startProcess = false;

    public Gazebo(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Газебо";
        text = "Никто не может тебе помогать. Ты " +
                "должен предстать пред Газебо один.";
        obscenityText = "Теряешь 3 уровня";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 8;
        iconPath = "/images/gazebo.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) {
        player.lvlUp(-3);
    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {
        var orders = fight.getFightOrders();

        if (orders.size() > 0) {
            if (!startProcess) {
                startProcess = true;

                var action = new ActionDiscardFightOrders(fight);
                if (action.canAmI(getMunchkinContext()))
                    getMunchkinContext().getActionHandler()
                            .doAction(action);

                getMunchkinContext().getActionHandler().updateContext();
                startProcess = false;
            }
        }
        return level;

    }
}
