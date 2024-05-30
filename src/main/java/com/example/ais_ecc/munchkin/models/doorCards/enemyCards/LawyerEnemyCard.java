package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class LawyerEnemyCard extends EnemyCard {
    public LawyerEnemyCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Адвокат";
        text = "Не станет атаковать Вора (профес" +
                "сиональная этика). Вор, столкнув" +
                "шийся с адвокатом, может вмесго" +
                "боя сбросить 2 Сокровища и взять 2" +
                "новых «втемную•.";
        obscenityText = "он достает тебя су" +
                "дебными придирками. Дай каждому" +
                "игроку вытянуть по одной карше из" +
                "твоей «руки», начиная с левого сосе" +
                "Сбрось остатки.";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 6;
        this.iconPath =  "/images/layer.png";
    }

    @Override
    public void obscenity(Player player) {

    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
