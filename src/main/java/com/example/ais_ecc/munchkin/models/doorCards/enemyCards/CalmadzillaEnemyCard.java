package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class CalmadzillaEnemyCard extends EnemyCard {


    public CalmadzillaEnemyCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Кальмадзилла";
        text = "Склизко! Эльфы в -4! Не преследует " +
                "никого, чей Уровень 4 или ниже, за" +
                "исключением Эльфов.";
        obscenityText = "хватает, сдавливает" +
                "противными щупальцами и пожи" +
                "рает. Ты мертв. Вопросы, пожелания," +
                "высказывания?";
        rewardLevel = 2;
        rewardTreasure = 4;
        level = 18;
    }

    @Override
    public void obscenity(Fight fight, Player player) {
        player.lvlUp(-1);
    }

    @Override
    public int getTotalPower() {
        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
