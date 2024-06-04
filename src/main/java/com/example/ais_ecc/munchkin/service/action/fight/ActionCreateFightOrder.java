package com.example.ais_ecc.munchkin.service.action.fight;

import com.example.ais_ecc.munchkin.models.OrderFight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionCreateFightOrder extends IAction {


    int treasureCount;
    private Player player;
    private Move move;
    private Fight fight;
    private MunchkinContext context;

    public ActionCreateFightOrder(Player player, int treasureCount) {
        this.player = player;
        this.treasureCount = treasureCount;
    }

    private ActionCreateFightOrder(MunchkinContext munchkinContext) {
        this.path = "create_fight_order/" + munchkinContext.getId();
        this.name = "Create Fight Order";
    }

    public static ActionCreateFightOrder createAction(MunchkinContext munchkinContext) {
        return new ActionCreateFightOrder(munchkinContext);
    }


    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        if (!context.isGameStarted)
            return false;

        move = context.getLastMove();
        if (move == null)
            return false;

        fight = move.getFight();
        if (fight == null)
            return false;

        player = munchkinContext.getCurrentPlayer();

        // Если игрок не в бою
        if (player.getId() == fight.getPlayer().getId())
            return false;

        // Нельзя создавать новые если уже есть принятые
        for (var order : fight.getFightOrders())
            if (order.player.getId().equals(player.getId()))
                if (order.trust)
                    return false;

        if (fight.getFlushings() != null)
            return false;

        return true;
    }

    @Override
    public String start() {
        var fightOrder = new OrderFight();
        if (treasureCount < 0)
            treasureCount = 0;

        fightOrder.setPlayer(player);
        fightOrder.setTreasureCount(treasureCount);
        fight.getFightOrders().add(fightOrder);

        return player.getUser().getUsername() + " create fight order. He required " + treasureCount + " treasures";
    }
}
