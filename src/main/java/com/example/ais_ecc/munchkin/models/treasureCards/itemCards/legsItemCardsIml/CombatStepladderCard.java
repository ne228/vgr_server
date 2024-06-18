package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml;

import com.example.ais_ecc.munchkin.models.Player;

import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.LegsItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class CombatStepladderCard extends LegsItemCard {

    public CombatStepladderCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Боевая стремянка";
        text = "+3 бонус";
        cost = 400;
        iconPath = "/images/combatStepladderCard.png";
        power = 3;
        setBigSize(true);

    }
    @Override
    public boolean canPutItem(Player player) {
        if (player.isRace(RaceList.HALFING))
            return true;
        else
            return false;
    }
}
