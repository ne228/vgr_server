package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.ActionDie;

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
    public void obscenity(Fight fight, Player player) throws Exception {
        var die = new ActionDie(player);
        if (die.canAmI(getMunchkinContext()))
            getMunchkinContext().getActionHandler().doAction(die);
    }

    @Override
    public int getTotalPower(Fight fight) {
        if (fight.getFightPlayers().stream().anyMatch(player -> player.isRace(RaceList.ELF)))
            return level + 4;

        return level;
    }

    @Override
    public boolean canChaise(Player player) {

        if (player.isRace(RaceList.ELF))
            return true;

        if (player.getLvl() <= 4)
            return false;


        return true;
    }
}
