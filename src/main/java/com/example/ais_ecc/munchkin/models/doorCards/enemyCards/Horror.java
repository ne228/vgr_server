package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.classes.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.ActionDie;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Horror extends EnemyCard {

    public Horror(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "НЕВЫРАЗИМО ЖУТКИЙ И НЕОПИСУЕМЫЙ УЖАС";
        text = "+4 против Воинов.";
        obscenityText = "Непотребство: Невыразимо жуткая " +
                "смерть, если ты не Волшебник. " +
                "Волшебник " +
                "просто " +
                "неописуемо теряет свою силу - " +
                "сбрось карту " +
                "Волшебника.";
        rewardLevel = 1;
        rewardTreasure = 4;
        level = 14;
        this.iconPath = "/images/horror.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        if (!player.isClass(ClassList.WIZARD)) {
            var act = new ActionDie(player);
            getMunchkinContext().getActionHandler().doAction(act);
        } else {
            var wizard = player.getClasses()
                    .stream().filter(_class -> _class.get_class() == ClassList.WIZARD)
                    .findFirst().get();
            player.getClasses().remove(wizard);
            wizard.discard(player);
        }

    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (fight.getFightPlayers().stream().anyMatch(player -> player.isRace(RaceList.DWARF)))
            return level + 5;

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
