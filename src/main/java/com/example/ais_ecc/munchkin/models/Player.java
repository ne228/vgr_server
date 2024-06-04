package com.example.ais_ecc.munchkin.models;


import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.munchkin.models.classes.ClassList;
import com.example.ais_ecc.munchkin.models.classes.Classes;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.models.races.Races;
import com.example.ais_ecc.munchkin.models.treasureCards.TreasureCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ArmorItemCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.HeadItemCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.LegsItemCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Player {

    private String id;
    private User user;

    private Gender gender;

    private int lvl = 1;
    private List<Classes> classes;
    private List<Races> races;
    private int canUseBigСlothesCount = 1;
    private int canHandCardCount = 5;

    private ArrayList<TreasureCard> openTreasureCards;

    private int defBonusFlushing = 0;

    private List<Card> cards;
    private int gold = 0;

    private HeadItemCard headItemCard;
    private LegsItemCard legsItemCard;
    private ArmorItemCard armorItemCard;
    private WeaponItemCard weaponItemCard_1;
    private WeaponItemCard weaponItemCard_2;

    // Выставленные бонусные карты
    // Выставленные оружие и тд
    public Player(User user) {
        this.user = user;
        cards = new ArrayList<>();
        races = new ArrayList<>();
        classes = new ArrayList<>();
        openTreasureCards = new ArrayList<>();

        Gender[] genders = Gender.values();
        Random random = new Random();
        int randomIndex = random.nextInt(genders.length);
        // Выбор случайного элемента из Enum
        Gender randomGender = genders[randomIndex];
        gender = randomGender;
    }

    @JsonIgnore
    public boolean isHaveCard(Card card) {
        var cardInDeck = getCards().stream().filter(c -> c.getId().equalsIgnoreCase(card.getId())).findFirst();
        if (cardInDeck.isPresent())
            return true;

        var cardInDeckTreasure = getOpenTreasureCards().stream().filter(c -> c.getId().equalsIgnoreCase(card.getId())).findFirst();
        if (cardInDeckTreasure.isPresent())
            return true;

        if (headItemCard != null)
            if (headItemCard.getId().equalsIgnoreCase(card.getId()))
                return true;

        if (armorItemCard != null)
            if (armorItemCard.getId().equalsIgnoreCase(card.getId()))
                return true;

        if (legsItemCard != null)
            if (legsItemCard.getId().equalsIgnoreCase(card.getId()))
                return true;

        if (weaponItemCard_1 != null)
            if (weaponItemCard_1.getId().equalsIgnoreCase(card.getId()))
                return true;

        if (weaponItemCard_2 != null)
            if (weaponItemCard_2.getId().equalsIgnoreCase(card.getId()))
                return true;

        return false;
    }

    public int getTotalPower() {
        return getItemPower() + lvl;
    }

    private int getItemPower() {
        int power = 0;

        if (headItemCard != null)
            power += headItemCard.getPower();

        if (armorItemCard != null)
            power += armorItemCard.getPower();

        if (legsItemCard != null)
            power += legsItemCard.getPower();

        if (weaponItemCard_1 != null)
            power += weaponItemCard_1.getPower();

        if (weaponItemCard_2 != null)
            power += weaponItemCard_2.getPower();

        return power;
    }

    public void lvlUp(int lvl) {
        this.lvl += lvl;

        if (this.lvl < 0)
            this.lvl = 1;
    }


    // CLASS ACTIONS
    public boolean isClass(ClassList _class) {
        for (Classes m_class : getClasses())
            if (_class == m_class.get_class())
                return true;
        return false;
    }


    // RACE ACTIONS

    public boolean isRace(RaceList race) {
        for (Races playerRace : getRaces())
            if (race == playerRace.getRace())
                return true;
        return false;
    }

    // ITEMS ACTIONS


    public List<Classes> getClasses() {
        return classes;
    }

    public void setClasses(List<Classes> classes) {
        this.classes = classes;
    }

    public List<Races> getRaces() {
        return races;
    }

    public void setRaces(List<Races> races) {
        this.races = races;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getId() {
        if (id == null)
            setId(UUID.randomUUID().toString());
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getCanUseBigСlothesCount() {
        return canUseBigСlothesCount;
    }

    public void setCanUseBigСlothesCount(int canUseBigСlothesCount) {
        this.canUseBigСlothesCount = canUseBigСlothesCount;
    }

    public int getCanHandCardCount() {
        return canHandCardCount;
    }

    public void setCanHandCardCount(int canHandCardCount) {
        this.canHandCardCount = canHandCardCount;
    }

    public int getDefBonusFlushing() {
        return defBonusFlushing;
    }

    public void setDefBonusFlushing(int defBonusFlushing) {
        this.defBonusFlushing = defBonusFlushing;
    }

    public HeadItemCard getHeadItemCard() {
        return headItemCard;
    }

    public void setHeadItemCard(HeadItemCard headItemCard) {
        this.headItemCard = headItemCard;
    }

    public LegsItemCard getLegsItemCard() {
        return legsItemCard;
    }

    public void setLegsItemCard(LegsItemCard legsItemCard) {
        this.legsItemCard = legsItemCard;
    }

    public ArmorItemCard getArmorItemCard() {
        return armorItemCard;
    }

    public void setArmorItemCard(ArmorItemCard armorItemCard) {
        this.armorItemCard = armorItemCard;
    }

    public WeaponItemCard getWeaponItemCard_1() {
        return weaponItemCard_1;
    }

    public void setWeaponItemCard_1(WeaponItemCard weaponItemCard_1) {
        this.weaponItemCard_1 = weaponItemCard_1;
    }

    public WeaponItemCard getWeaponItemCard_2() {
        return weaponItemCard_2;
    }

    public void setWeaponItemCard_2(WeaponItemCard weaponItemCard_2) {
        this.weaponItemCard_2 = weaponItemCard_2;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public ArrayList<TreasureCard> getOpenTreasureCards() {
        return openTreasureCards;
    }

    public void setOpenTreasureCards(ArrayList<TreasureCard> openTreasureCards) {
        this.openTreasureCards = openTreasureCards;
    }
}
