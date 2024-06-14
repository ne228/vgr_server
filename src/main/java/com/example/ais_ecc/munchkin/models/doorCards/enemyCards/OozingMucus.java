package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropLegsOr1Lvl;

public class OozingMucus extends EnemyCard {

    public OozingMucus(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Сочащаяся Слизь";
        text = "Ну и мерзость! +4 против Эльфов.";
        obscenityText = "Непотребство: Сбрось надетую " +
                "Обувку. Если уже без Обувки, " +
                "теряешь Уровень.";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 1;
        this.iconPath = "/images/oozingMucus.png";}

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var act = new ActionDropLegsOr1Lvl(player);
        if (act.canAmI(getMunchkinContext()))
            getMunchkinContext().getActionHandler().doAction(act);

    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (fight.getFightPlayers().stream().anyMatch(player -> player.isRace(RaceList.ELF)))
            return level + 4;

        return level;
    }
}
