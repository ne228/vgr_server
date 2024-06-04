package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class CitizenBones extends EnemyCard {

    public CitizenBones(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Гражданин Кости";
        text = "Если вынужден смываться, то теряешь 1 уровень.";
        obscenityText = "его костлявое прикосновения снимает с тебя 2 уроавн";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 2;
        unDead = true;
        iconPath = "/images/citizenBones.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) {
        player.lvlUp(-2);
    }

    @Override
    public int getTotalPower() {

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }

    @Override
    public void flushing(Player player) {
        munchkinContext.getMessages().add("Игрок " + player.getUser().getUsername() + " теряет 1 уровень");
        player.lvlUp(-1);
    }
}
