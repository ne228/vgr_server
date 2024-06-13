package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.classes.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropRaceAndClass;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ScreamingNerd extends EnemyCard {

    public ScreamingNerd(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Вопящий задрот";
        text = "+6 против Воинов.";
        obscenityText = "Становишься " +
                "обычным " +
                "скучным " +
                "человеком. " +
                "Сбрось все " +
                "карты Расы и " +
                "Класса, которые " +
                "у тебя в игре.";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 6;
        this.iconPath = "/images/screamingNerd.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var action = new ActionDropRaceAndClass(player);
        getMunchkinContext().getActionHandler()
                .doAction(action);
    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (fight.getFightPlayers().size() == 1 && fight.getHelpTreasureCards().size() == 0)
            rewardLevel = 2;
        else
            rewardLevel = 1;

        if (fight.getFightPlayers().stream().anyMatch(player -> player.isClass(ClassList.WARRIOR)))
            return level + 6;

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
