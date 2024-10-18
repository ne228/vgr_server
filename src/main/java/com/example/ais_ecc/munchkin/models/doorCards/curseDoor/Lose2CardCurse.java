package com.example.ais_ecc.munchkin.models.doorCards.curseDoor;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.curse.curseImpl.ActionPullCardHand;

import java.util.UUID;

public class Lose2CardCurse extends CurseDoorCard {
    public Lose2CardCurse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.title = "Проклятие. Теряешь 2 карты";
        this.text = "Сначала игрок слева от жертвы тянет карту из \"руки\" жертвы и оставляет ее себе. Затем то же самое делает игрок справа.";
        this.iconPath = "/images/lose2CardCurse.png";
    }

    @Override
    public void curseDo(Player player) throws Exception {
        var players = munchkinContext.getPlayers();
        var indexPlayer = players.indexOf(player);

        var playerRight = players.get((indexPlayer + 1) % players.size());
        var playerLeftIndex = (indexPlayer - 1) % players.size();
        if (playerLeftIndex < 0)
            playerLeftIndex *= -1;
        var playerLeft = players.get(playerLeftIndex);

        var pullCardHandRight = new ActionPullCardHand(player, playerRight, UUID.randomUUID().toString(), getMunchkinContext());
        var pullCardHandLeft = new ActionPullCardHand(player, playerLeft, UUID.randomUUID().toString(), getMunchkinContext());
        getMunchkinContext().getActionHandler().addRequiredAction(pullCardHandRight);
        getMunchkinContext().getActionHandler().addRequiredAction(pullCardHandLeft);
    }

    @Override
    public void curseRemove(Player player) throws Exception {

    }
}
