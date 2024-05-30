package com.example.ais_ecc.munchkin.service.action.fight;

import com.example.ais_ecc.munchkin.models.OrderFight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.ArrayList;
import java.util.List;

public class ActionConfirmFightOrder extends IAction {


    int treasureCount;
    private String orderId;
    private boolean confirm;
    private Player player;
    private Move move;
    private Fight fight;
    private OrderFight orderFight;
    private MunchkinContext context;

    public ActionConfirmFightOrder(String orderId, boolean confirm) {
        this.orderId = orderId;
        this.confirm = confirm;
    }

    private ActionConfirmFightOrder(MunchkinContext munchkinContext) {
        this.path = "confirm_fight_order/" + munchkinContext.getId();
        this.name = "Confirm Fight Order";
    }

    public static ActionConfirmFightOrder createAction(MunchkinContext munchkinContext) {
        return new ActionConfirmFightOrder(munchkinContext);
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


        if (fight.getFightOrders().size() == 0)
            return false;


        var sizeTrustOrder = fight.getFightOrders()
                .stream()
                .filter(_order -> _order.isTrust())
                .count();

        if (sizeTrustOrder == fight.getFightOrders().size())
            return false;


        // Если игрок не в бою
        if (player.getId() == fight.getPlayer().getId())
            return true;


        return false;
    }

    @Override
    public String start() {

        OrderFight removeOrder = null;
        for (var order : fight.getFightOrders())
            if (order.getId().equals(orderId)) {
                orderFight = order;
                if (confirm)
                    order.setTrust(true);
                else
                    removeOrder = order;
                break;
            }

        if (removeOrder != null) {
            fight.getFightOrders().remove(removeOrder);
            return player.getUser().getUsername() + " discard fight order from " + orderFight.getPlayer().getUser().getUsername();
        }


        // Clear order this users
        List<OrderFight> removeOrders = new ArrayList<>();
        for (var order : fight.getFightOrders())
            if (order.getPlayer().getId().equals(orderFight.getPlayer().getId()))
                if (!order.trust)
                    removeOrders.add(order);

        fight.getFightOrders().removeAll(removeOrders);


        return player.getUser().getUsername() + " confirm fight order from " + orderFight.getPlayer().getUser().getUsername();
    }
}
