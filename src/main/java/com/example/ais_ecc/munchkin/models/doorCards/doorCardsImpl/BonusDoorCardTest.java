package com.example.ais_ecc.munchkin.models.doorCards.doorCardsImpl;

//import com.bezkoder.vgr.munchkin.models.doorCards.BonusDoorCard;

import com.example.ais_ecc.munchkin.models.doorCards.BonusDoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class BonusDoorCardTest extends BonusDoorCard {


    public BonusDoorCardTest(MunchkinContext munchkinContext) {
        super(munchkinContext);
        setTitle("BonusDoorCardTest");
    }
}
