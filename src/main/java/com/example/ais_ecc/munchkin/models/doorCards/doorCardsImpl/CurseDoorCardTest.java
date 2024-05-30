package com.example.ais_ecc.munchkin.models.doorCards.doorCardsImpl;


import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.CurseDoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.List;

public class CurseDoorCardTest extends CurseDoorCard {


    public CurseDoorCardTest(MunchkinContext munchkinContext) {
        super(munchkinContext);
        setTitle("CurseDoorCardTest");
    }

    @Override
    public void curseDo(Player player) {

    }



}
