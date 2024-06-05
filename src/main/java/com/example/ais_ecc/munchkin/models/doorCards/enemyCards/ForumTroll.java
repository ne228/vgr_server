package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.classes.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionPickUpItemCard;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class ForumTroll extends EnemyCard {
    public ForumTroll(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Форумный трольь";
        text = "У него нет особых способностей, и это жутко бесит";
        obscenityText = "Уничтожает игрвоой баланс, разрешая игрокам наивысшего уровня взять у тебя по одной шмотке на их вкус";
        rewardLevel = 1;
        rewardTreasure = 3;
        level = 10;
        unDead = false;
        iconPath = "/images/forumTroll.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {

        var players = new ArrayList<Player>();
        players.addAll(getMunchkinContext().getPlayers());
        var fightPlayers = new ArrayList<Player>();
        fightPlayers.addAll(fight.getFightPlayers());
        players.removeAll(fightPlayers);

        int maxLvl = players.stream()
                .map(Player::getLvl)
                .max(Integer::compare)
                .get();

        var maxLvlPlayers = players.stream()
                .filter(x -> x.getLvl() == maxLvl)
                .collect(Collectors.toList());

        for (var maxLvlPlayer : maxLvlPlayers) {
            var scopeId = UUID.randomUUID().toString();
            if (player.getHeadItemCard() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, maxLvlPlayer, player.getHeadItemCard(), player));

            if (player.getArmorItemCard() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, maxLvlPlayer, player.getArmorItemCard(), player));


            if (player.getLegsItemCard() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, maxLvlPlayer, player.getLegsItemCard(), player));

            if (player.getWeaponItemCard_1() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, maxLvlPlayer, player.getWeaponItemCard_1(), player));

            if (player.getWeaponItemCard_2() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, maxLvlPlayer, player.getWeaponItemCard_2(), player));

        }

    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {

        if (fight != null) {
            if (fight.getPlayer().isClass(ClassList.CLERIC))
                return level + 3;

            for (var helpPlayerOrder : fight.getFightOrders()) {
                if (helpPlayerOrder.getPlayer().isClass(ClassList.CLERIC))
                    return level + 3;
            }
        }
        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }


}
