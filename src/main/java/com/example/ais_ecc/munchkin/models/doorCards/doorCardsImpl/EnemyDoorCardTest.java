package com.example.ais_ecc.munchkin.models.doorCards.doorCardsImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class EnemyDoorCardTest extends EnemyCard {


    public EnemyDoorCardTest(MunchkinContext munchkinContext) {
        super(munchkinContext);
        setTitle("EnemyDoorCardTest");
    }

    @Override
    public void obscenity(Player player) {

    }

    @Override
    public int getTotalPower() {
        return 0;
    }

    @Override
    public boolean canChaise(Player player) {
        return false;
    }


}
