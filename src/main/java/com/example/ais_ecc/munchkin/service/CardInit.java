package com.example.ais_ecc.munchkin.service;

import com.example.ais_ecc.munchkin.models.doorCards.BonusDoorCard;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.models.doorCards.bonusDoorCards.PsychoCard;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.*;
import com.example.ais_ecc.munchkin.models.doorCards.enemyCards.*;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.DwarfCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.ElfCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.HalfingCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.models.doorCards.walkingDeadCardsImpl.WalkingMonsterCard1;
import com.example.ais_ecc.munchkin.models.doorCards.walkingDeadCardsImpl.WalkingMonsterCard2;
import com.example.ais_ecc.munchkin.models.doorCards.walkingDeadCardsImpl.WalkingMonsterCard3;
import com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.bonusTreasureCardsImpl.PotionIdiotCourage;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.armoredItemCardsImpl.BurntArmorCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.headItemCards.HornedHelmetCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml.ReallyFastRunningShoesCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml.SandalsProtectorsCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards.CharmingDudaCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards.SwissArmyHalberdCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards.SwordSongDance;

public class CardInit {

    private MunchkinContext context;

    public CardInit(MunchkinContext context) {
        this.context = context;
    }

    public void initDeckDoor() {
        DoorCard calmadzillaEnemyCard = new CalmadzillaEnemyCard(context);
        DoorCard lawyerEnemyCard = new LawyerEnemyCard(context);


        // GEN RACE CARDS
        RaceCard dwarfCard = new DwarfCard(context);
        RaceCard elfCard = new ElfCard(context);
        RaceCard halfingCard = new HalfingCard(context);

        // GEN CLASS CARDS
        ClassesCard clericCard = new ClericCard(context);
        ClassesCard thiefCard = new ThiefCard(context);
        ClassesCard warriorCard = new WarriorCard(context);
        ClassesCard wizardCard = new WizardCard(context);

        context.getDoorCards().add(new CitizenBones(context));
        context.getDoorCards().add(lawyerEnemyCard);
        context.getDoorCards().add(lawyerEnemyCard);
        context.getDoorCards().add(lawyerEnemyCard);
        //        getDoorCards().add(calmadzillaEnemyCard);

        //        getDoorCards().add(curse);
        //        getDoorCards().add(bonus);

        //        getDiscardCards().add(new WarriorCard(this));
        //        getDiscardCards().add(new WizardCard(this));
        //        getDiscardCards().add(new ClericCard(this));
        //        getDiscardCards().add(new DwarfCard(this));
    }

    public void initDeckTreasure() {

        context.getTreasureCards().add(new BurntArmorCard(context));
        context.getTreasureCards().add(new BurntArmorCard(context));

        context.getTreasureCards().add(new HornedHelmetCard(context));
        context.getTreasureCards().add(new HornedHelmetCard(context));

        context.getTreasureCards().add(new SandalsProtectorsCard(context));
        context.getTreasureCards().add(new SandalsProtectorsCard(context));


        // BONUS TREASURE CARDS
        context.getTreasureCards().add(new PotionIdiotCourage(context));
        context.getTreasureCards().add(new PotionIdiotCourage(context));
        context.getTreasureCards().add(new PotionIdiotCourage(context));

    }

    public void initPlayerDeck() {
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

            // Enemies Cards
            i_player.getCards().add(new CalmadzillaEnemyCard(context));

            i_player.getCards().add(new ForumTroll(context));
            i_player.getCards().add(new HammerRatEnemyCard(context));
            i_player.getCards().add(new CitizenBones(context));
            i_player.getCards().add(new Pitbull(context));
            i_player.getCards().add(new ScreamingNerd(context));
            i_player.getCards().add(new Uticora(context));
            i_player.getCards().add(new Burp(context));
            i_player.getCards().add(new Burp(context));
            i_player.getCards().add(new PlutoniumDragon(context));
            i_player.getCards().add(new PlutoniumDragon(context));
            i_player.getCards().add(new PottedGrass(context));
            i_player.getCards().add(new PottedGrass(context));


            i_player.getCards().add(new ClericCard(context));
            i_player.getCards().add(new WizardCard(context));

            /// ITEM CARDS

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
            i_player.getCards().add(new ReallyFastRunningShoesCard(context));
            // BONUS TREASURE CARDS
            i_player.getCards().add(new PotionIdiotCourage(context));
            i_player.getCards().add(new PotionIdiotCourage(context));
            i_player.getCards().add(new PotionIdiotCourage(context));


            i_player.getCards().add(new WalkingMonsterCard1(context));
            i_player.getCards().add(new WalkingMonsterCard2(context));
            i_player.getCards().add(new WalkingMonsterCard3(context));

        }
    }
}
