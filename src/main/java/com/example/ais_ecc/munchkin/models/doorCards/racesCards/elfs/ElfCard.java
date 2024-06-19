package com.example.ais_ecc.munchkin.models.doorCards.racesCards.elfs;

import com.example.ais_ecc.munchkin.models.OrderFight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.fight.ActionFightEnd;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

public abstract class ElfCard extends RaceCard {
    ISubscribe subscribe;

    public ElfCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Elf";
        this.race = RaceList.ELF;
    }

    @Override
    public void accept(Player player) {
        player.setDefBonusFlushing(player.getDefBonusFlushing() + 1);

        subscribe = new ISubscribe(new ActionFightEnd(player)) {
            @Override
            public void update() {
                try {
                    var fight = getMunchkinContext().getFight();
                    var orders = fight.getFightOrders().stream().filter(OrderFight::isTrust);
                    if (orders.anyMatch(orderFight -> player.getId().equalsIgnoreCase(orderFight.getPlayer().getId()))) {
                        for(var enemy : fight.getEnemyCards())
                            player.lvlUp(1);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        };
        getMunchkinContext().getActionHandler().getSubscribeService().register(subscribe);
    }

    @Override
    public void discard(Player player) {
        player.setDefBonusFlushing(player.getDefBonusFlushing() - 1);
        getMunchkinContext().getActionHandler().getSubscribeService().unRegister(subscribe);
    }

}
