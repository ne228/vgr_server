package com.example.ais_ecc.munchkin.service.action;

import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.munchkin.models.doorCards.BonusDoorCard;
import com.example.ais_ecc.munchkin.models.doorCards.bonusDoorCards.PsychoCard;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.*;
import com.example.ais_ecc.munchkin.models.doorCards.doorCardsImpl.BonusDoorCardTest;
import com.example.ais_ecc.munchkin.models.doorCards.doorCardsImpl.CurseDoorCardTest;

import com.example.ais_ecc.munchkin.models.doorCards.enemyCards.CalmadzillaEnemyCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.DwarfCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.ElfCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.HalfingCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.bonusTreasureCardsImpl.PotionIdiotCourage;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.armoredItemCardsImpl.BurntArmorCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.headItemCards.HornedHelmetCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml.SandalsProtectorsCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards.CharmingDudaCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards.SwissArmyHalberdCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards.SwordSongDance;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class ActionStart extends IAction {

    private User user;


    public ActionStart(MunchkinContext context) {
        this.path = "start/" + context.getId();
        this.name = "Start";
        this.title = "Start";
        this.color = "success";
    }

    public ActionStart(User user) {
        this.user = user;
    }

    public static ActionStart createAction(MunchkinContext context) {
        return new ActionStart(context);
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        if (!context.isConnected())
            return false;

        if (context.getPlayers().size() < context.getNeedPlayersForStart())
            return false;
//            throw new Exception("Need more then "+ context.get1NeedPlayersForStart() +"players. Current count " + context.getPlayers().size());

        return !context.isGameStarted;
    }

    @Override
    public String start() throws Exception {
//        if (context.isGameStarted) throw new Exception("Game already started");
        try {
            context.initDoorCards();
            var player = context.getPlayers().get(0);
            var move = new Move(player, context);
            context.getMoves().add(move);
            context.isGameStarted = true;
            for (var i_player : context.getPlayers()) {
                // GEN RACE CARDS
                RaceCard dwarfCard = new DwarfCard(context);
                RaceCard elfCard = new ElfCard(context);
                RaceCard halfingCard = new HalfingCard(context);

                // GEN CLASS CARDS
                ClassesCard clericCard = new ClericCard(context);
                ClassesCard thiefCard = new ThiefCard(context);
                ClassesCard warriorCard = new WarriorCard(context);
                ClassesCard wizardCard = new WizardCard(context);

                // BONUS DOOR CARDS
                BonusDoorCard psychoCard = new PsychoCard(context);
                i_player.getCards().add(psychoCard);

                i_player.getCards().add(dwarfCard);
                i_player.getCards().add(elfCard);
                i_player.getCards().add(halfingCard);

                i_player.getCards().add(clericCard);
                i_player.getCards().add(thiefCard);
                i_player.getCards().add(warriorCard);
                i_player.getCards().add(wizardCard);


                i_player.getCards().add(new BonusDoorCardTest(context));
                i_player.getCards().add(new CalmadzillaEnemyCard(context));
                i_player.getCards().add(new CurseDoorCardTest(context));
                i_player.getCards().add(new ClericCard(context));
                i_player.getCards().add(new WizardCard(context));


                i_player.getCards().add(new BurntArmorCard(context));
                i_player.getCards().add(new BurntArmorCard(context));

                i_player.getCards().add(new HornedHelmetCard(context));
                i_player.getCards().add(new HornedHelmetCard(context));

                i_player.getCards().add(new SandalsProtectorsCard(context));
                i_player.getCards().add(new SandalsProtectorsCard(context));

                i_player.getCards().add(new CharmingDudaCard(context));
                i_player.getCards().add(new CharmingDudaCard(context));

                i_player.getCards().add(new SwissArmyHalberdCard(context));
                i_player.getCards().add(new SwissArmyHalberdCard(context));

                i_player.getCards().add(new SwordSongDance(context));
                i_player.getCards().add(new SwordSongDance(context));

                // BONUS TREASURE CARDS
                i_player.getCards().add(new PotionIdiotCourage(context));
                i_player.getCards().add(new PotionIdiotCourage(context));
                i_player.getCards().add(new PotionIdiotCourage(context));

            }
        } catch (Exception exc) {
            throw new Exception("Start game error: " + exc.getMessage());
        }
        return "Game context success start";
    }
}
