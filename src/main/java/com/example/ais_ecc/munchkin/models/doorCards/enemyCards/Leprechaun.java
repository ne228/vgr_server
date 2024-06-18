package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.BonusItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.RequiredAction;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropItemCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Leprechaun extends EnemyCard {

    public Leprechaun(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Лепрекон";
        text = "Какая гадость. +5 против Эльфов";
        obscenityText = "Непотребство: Забирает у тебя 2 " +
                "шмотки. Игроки с обеих сторон от " +
                "тебя определяют по одной вещи, с " +
                "которыми ты расстанешься.";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 4;
        this.iconPath = "/images/leprechaun.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {

        var context = getMunchkinContext();
        // Снять все шмотки
        List<Card> cards = new ArrayList<>();
        if (player.getHeadItemCard() != null) {
            cards.add(player.getHeadItemCard());
        }
        if (player.getArmorItemCard() != null) {
            cards.add(player.getArmorItemCard());
        }
        if (player.getLegsItemCard() != null) {
            cards.add(player.getLegsItemCard());
        }
        if (player.getWeaponItemCard_1() != null) {
            cards.add(player.getWeaponItemCard_1());
        }
        if (player.getWeaponItemCard_2() != null) {
            cards.add(player.getWeaponItemCard_2());
        }
        var bonusItemCards = new ArrayList<BonusItemCard>();
        bonusItemCards.addAll(player.getBonusItemCards());

        for (var bonusItemCard : bonusItemCards)
            cards.add(bonusItemCard);


        var fightPlayers = fight.getFightPlayers();
        var players = new ArrayList<Player>();
        players.addAll(getMunchkinContext().getPlayers());
        players.removeAll(fightPlayers);


        Player playerPlus1;
        Player playerMin1;
        if (players.size() == 0) {
            playerMin1 = player;
            playerPlus1 = player;
        } else {
            int randInt = new Random().nextInt(10);

            var playerPlus1I = (randInt + 1) % players.size();
            var playerMin1I = (randInt + 2) % players.size();

            playerPlus1 = players.get(playerPlus1I);
            playerMin1 = players.get(playerMin1I);
        }

        var scopeId1 = UUID.randomUUID().toString();
        var scopeId2 = UUID.randomUUID().toString();


        boolean onePlayer = playerPlus1.getId().equalsIgnoreCase(playerMin1.getId());
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);

            RequiredAction act = new ActionDropItemCard(scopeId1, playerMin1, card, player);
            getMunchkinContext().getActionHandler().addRequiredAction(act);

            RequiredAction act2 = new ActionDropItemCard(scopeId2, playerPlus1, card, player);
            getMunchkinContext().getActionHandler().addRequiredAction(act2);
        }

    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (fight.getFightPlayers().stream().anyMatch(player -> player.isRace(RaceList.ELF)))
            return level + 5;

        return level;
    }
}
