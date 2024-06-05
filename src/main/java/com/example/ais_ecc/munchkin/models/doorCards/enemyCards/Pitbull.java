package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class Pitbull extends EnemyCard {
    public Pitbull(MunchkinContext munchkinContext) {
        // TODO особое действия карточки pitbull
        super(munchkinContext);
        title = "Питбуль";
        text = "Если ты не можешь победить его, " +
                "попробуй отвлечь (автоматическая Смывка) " +
                "брошенным " +
                "жезлом, " +
                "посохом. древком. " +
                "”Тузик, апорт!“";
        obscenityText = "" +
                "Отмечается " +
                "клыками на " +
                "твоей заднице. " +
                "Теряешь " +
                "2 Уровня.";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 2;
        this.iconPath =  "/images/pitbull.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) {
        player.lvlUp(-2);
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
