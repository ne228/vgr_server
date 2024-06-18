package com.example.ais_ecc.munchkin.service;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.doorCards.BonusDoorCard;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.models.doorCards.bonusDoorCards.PsychoCard;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.clerics.Cleric1;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.clerics.Cleric2;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.clerics.Cleric3;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.clerics.ClericCard;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.thiefs.Thief1;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.thiefs.Thief2;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.thiefs.Thief3;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.warriors.Warrior1;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.warriors.Warrior2;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.warriors.Warrior3;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.wizards.Wizard1;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.wizards.Wizard2;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.wizards.Wizard3;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.wizards.WizardCard;
import com.example.ais_ecc.munchkin.models.doorCards.enemyCards.*;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.dwarfs.Dwarf1;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.dwarfs.Dwarf2;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.dwarfs.Dwarf3;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.elfs.Elf1;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.elfs.Elf2;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.elfs.Elf3;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.haflings.Halfing1;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.haflings.Halfing2;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.haflings.Halfing3;
import com.example.ais_ecc.munchkin.models.doorCards.walkingDeadCardsImpl.WalkingMonsterCard1;
import com.example.ais_ecc.munchkin.models.doorCards.walkingDeadCardsImpl.WalkingMonsterCard2;
import com.example.ais_ecc.munchkin.models.doorCards.walkingDeadCardsImpl.WalkingMonsterCard3;
import com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.CheaterCube;
import com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.bonusTreasureCardsImpl.PotionIdiotCourage;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.armoredItemCardsImpl.BurntArmorCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.armoredItemCardsImpl.LeatherOutfitCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.bonusItemCards.SpikyKnees;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.headItemCards.HornedHelmetCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.headItemCards.TheHelmetOfFearlessnessCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml.CombatStepladderCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml.ReallyFastRunningShoesCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml.SandalsProtectorsCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml.TheShoesOfTheMightyPendelCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards.*;

import java.util.ArrayList;

public class CardInit {

    private MunchkinContext context;

    public CardInit(MunchkinContext context) {
        this.context = context;
    }

    private ArrayList<Card> getClassesCards() {
        var res = new ArrayList<Card>();
        res.add(new Cleric1(context));
        res.add(new Cleric2(context));
        res.add(new Cleric3(context));

        res.add(new Wizard1(context));
        res.add(new Wizard2(context));
        res.add(new Wizard3(context));

        res.add(new Warrior1(context));
        res.add(new Warrior2(context));
        res.add(new Warrior3(context));


        res.add(new Thief1(context));
        res.add(new Thief2(context));
        res.add(new Thief3(context));

        return res;
    }

    private ArrayList<Card> getRaceCards() {
        var res = new ArrayList<Card>();
        res.add(new Dwarf1(context));
        res.add(new Dwarf2(context));
        res.add(new Dwarf3(context));

        res.add(new Elf1(context));
        res.add(new Elf2(context));
        res.add(new Elf3(context));


        res.add(new Halfing1(context));
        res.add(new Halfing2(context));
        res.add(new Halfing3(context));

        return res;
    }

    public void initDeckDoor() {
        DoorCard calmadzillaEnemyCard = new CalmadzillaEnemyCard(context);
        DoorCard lawyerEnemyCard = new LawyerEnemyCard(context);


        // GEN RACE AND CLASS CARDS

        for (var card : getRaceCards())
            context.getDoorCards().add((DoorCard) card);

        for (var card : getClassesCards())
            context.getDoorCards().add((DoorCard) card);


//        context.getDoorCards().add(new CitizenBones(context));
//        context.getDoorCards().add(lawyerEnemyCard);
//        context.getDoorCards().add(lawyerEnemyCard);
//        context.getDoorCards().add(lawyerEnemyCard);


        //        getDoorCards().add(calmadzillaEnemyCard);

        //        getDoorCards().add(curse);
//                getDoorCards().add(bonus);

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
            i_player.getCards().addAll(getRaceCards());
            i_player.getCards().addAll(getClassesCards());

            // BONUS DOOR CARDS
            BonusDoorCard psychoCard = new PsychoCard(context);
            i_player.getCards().add(psychoCard);


            // Enemies Cards


            i_player.getCards().add(new WightBrothers(context));
            i_player.getCards().add(new Horror(context));
            i_player.getCards().add(new UndeadHorse(context));
            i_player.getCards().add(new TongueDemon(context));
            i_player.getCards().add(new Gazebo(context));
            i_player.getCards().add(new InsuranceAgent(context));
            i_player.getCards().add(new InsuranceAgent(context));
            i_player.getCards().add(new Bigfoot(context));
            i_player.getCards().add(new Bulrog(context));
            i_player.getCards().add(new FlyingFrogs(context));
            i_player.getCards().add(new LikeLouse(context));
            i_player.getCards().add(new OozingMucus(context));
            i_player.getCards().add(new CalmadzillaEnemyCard(context));
            i_player.getCards().add(new Leprechaun(context));
            i_player.getCards().add(new Leprechaun(context));
            i_player.getCards().add(new Orcs(context));
            i_player.getCards().add(new Orcs(context));
            i_player.getCards().add(new BigChip(context));
            i_player.getCards().add(new CrippledGoblin(context));
            i_player.getCards().add(new KingToot(context));
            i_player.getCards().add(new KingToot(context));
            i_player.getCards().add(new Mademonouselli(context));
            i_player.getCards().add(new Mademonouselli(context));
            i_player.getCards().add(new Harpists(context));
            i_player.getCards().add(new Harpists(context));
            i_player.getCards().add(new Hippogriff(context));
            i_player.getCards().add(new Hippogriff(context));

            i_player.getCards().add(new AcceleratedSnails(context));
            i_player.getCards().add(new AcceleratedSnails(context));
            i_player.getCards().add(new GelatinOctahedron(context));
            i_player.getCards().add(new GelatinOctahedron(context));
            i_player.getCards().add(new ForumTroll(context));
            i_player.getCards().add(new HammerRatEnemyCard(context));
            i_player.getCards().add(new CitizenBones(context));
            i_player.getCards().add(new Pitbull(context));

            i_player.getCards().add(new GelatinOctahedron(context));
            i_player.getCards().add(new ScreamingNerd(context));
            i_player.getCards().add(new Uticora(context));
            i_player.getCards().add(new Burp(context));
            i_player.getCards().add(new Burp(context));
            i_player.getCards().add(new PlutoniumDragon(context));
            i_player.getCards().add(new PlutoniumDragon(context));
            i_player.getCards().add(new PottedGrass(context));
            i_player.getCards().add(new PottedGrass(context));



            /// ITEM CARDS

            i_player.getCards().add(new BurntArmorCard(context));
            i_player.getCards().add(new BurntArmorCard(context));

            i_player.getCards().add(new LeatherOutfitCard(context));

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

            i_player.getCards().add(new TheShoesOfTheMightyPendelCard(context));

            i_player.getCards().add(new TheHelmetOfFearlessnessCard(context));

            i_player.getCards().add(new PretentiousBucklerCard(context));

            i_player.getCards().add(new AnElevenFootCueCard(context));

            i_player.getCards().add(new HugeRockCard(context));

            i_player.getCards().add(new NapalmStaffCard(context));

            i_player.getCards().add(new CombatStepladderCard(context));


            i_player.getCards().add(new SpikyKnees(context));
            i_player.getCards().add(new SpikyKnees(context));
            i_player.getCards().add(new SpikyKnees(context));

            // BONUS TREASURE CARDS
            i_player.getCards().add(new PotionIdiotCourage(context));
            i_player.getCards().add(new PotionIdiotCourage(context));
            i_player.getCards().add(new PotionIdiotCourage(context));


            i_player.getCards().add(new CheaterCube(context));
            i_player.getCards().add(new CheaterCube(context));
            i_player.getCards().add(new CheaterCube(context));


            i_player.getCards().add(new WalkingMonsterCard1(context));
            i_player.getCards().add(new WalkingMonsterCard2(context));
            i_player.getCards().add(new WalkingMonsterCard3(context));

        }
    }
}
