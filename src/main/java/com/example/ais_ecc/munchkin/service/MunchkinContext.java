package com.example.ais_ecc.munchkin.service;

import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.munchkin.models.*;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.models.treasureCards.TreasureCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ItemCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.actions.*;
import com.example.ais_ecc.munchkin.service.actions.card.ActionSellCard;
import com.example.ais_ecc.munchkin.service.actions.card.ActionTransferCard;
import com.example.ais_ecc.munchkin.service.actions.classes.*;
import com.example.ais_ecc.munchkin.service.actions.fight.*;
import com.example.ais_ecc.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class MunchkinContext {

    //    final private InstanceCreator instanceCreator;
    //    public List<Fight> fights = new ArrayList<>();
    public List<Player> players = new ArrayList<>();
    public List<Move> moves = new ArrayList<>();
    public boolean isGameStarted = false;
    @JsonIgnore
    @Autowired
    UserRepository userRepository;
    private String id;
    private List<DoorCard> doorCards;

    private List<Card> discardCards;
    private List<TreasureCard> treasureCards;

    private int needPlayersForStart = 1;

    private SimpMessagingTemplate messagingTemplate;


    private List<String> messages = new ArrayList<>();


    @JsonIgnore
    private ActionHandler actionHandler;

    @JsonIgnore
    private CardInit cardInit;
//    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
//    private String id;

    public MunchkinContext() {
        treasureCards = new ArrayList<>();
        discardCards = new ArrayList<>();
        doorCards = new ArrayList<>();
    }

    public void registerServices(SimpMessagingTemplate messagingTemplate, UserRepository userRepository) throws Exception {
        this.userRepository = userRepository;
        this.actionHandler = new ActionHandler(this, messagingTemplate);
        this.cardInit = new CardInit(this);
    }


    public <T> T getRandomElement(List<T> list) {
        // Проверяем, что список не пустой
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список не должен быть пустым");
        }

        // Создаем генератор случайных чисел
        Random random = new Random();

        // Генерируем случайный индекс
        int randomIndex = random.nextInt(list.size());

        // Возвращаем случайный элемент из списка
        return list.get(randomIndex);
    }

    public void discardCard(String cardId) throws Exception {
        for (var player : getPlayers()) {
            var card = ListExtensions.extractById(player.getCards(), cardId);
            if (card != null) {
                discardCards.add(card);
                return;
            }
        }

        var card = ListExtensions.extractById(doorCards, cardId);
        if (card != null) {
            discardCards.add(card);
            return;
        }

        var lastMove = getLastMove();
        if (lastMove == null)
            throw new Exception("Not found card. Card not discarded");

        var fight = lastMove.getFight();
        if (fight == null)
            throw new Exception("Not found card. Card not discarded");

        card = ListExtensions.extractById(lastMove.getFight().getDoorCards(), cardId);
        if (card != null) {
            discardCards.add(card);
            return;
        }

        card = ListExtensions.extractById(lastMove.getFight().getEnemyCards(), cardId);
        if (card != null) {
            discardCards.add(card);
            return;
        }


        throw new Exception("Not found card. Card not discarded");
    }

    private Player getPlayerByUser(User user) {
        for (Player player : players)
            if (player.getUser().getUsername().equalsIgnoreCase(user.getUsername()))
                return player;

        return null;
    }


    public Player getCurrentPlayer() {
        var user = getUser();
        for (Player player : players)
            if (player.getUser().getUsername().equalsIgnoreCase(user.getUsername()))
                return player;

        return null;
    }


    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, аутентифицирован ли пользователь и является ли объект Principal объектом User
        if (authentication != null) {
            // Получаем объект User
            var username = authentication.getName();
//            User user = userRepository.findByUsername(authentication.getName()).get();
            User user = (User) authentication.getPrincipal();
            return user;
        } else {
            System.out.println("Пользователь не аутентифицирован или не представлен объект User");
        }
        return null;
    }


    @JsonIgnore
    public Player getPlayerById(String id) {
        for (Player player : getPlayers()) {
            if (player.getId().equals(id)) {
                return player;
            }
        }
        return null; // Если игрок с заданным id не найден
    }

    public boolean isConnected() {
        var player = getPlayerByUser(getUser());
        if (player == null)
            return false;

        return true;
    }


    public void addPlayer(User user) throws Exception {
        ActionConnect actionConnect = new ActionConnect(user);
        actionHandler.doAction(actionConnect);
    }

    public void removePlayer(User user) throws Exception {
        ActionDisconnect actionDisconnect = new ActionDisconnect(user);
        actionHandler.doAction(actionDisconnect);
    }

    public void agreeFight() throws Exception {
        ActionFightAgree actionFightAgree = new ActionFightAgree(getCurrentPlayer());
        actionHandler.doAction(actionFightAgree);
    }

    public void startGame() throws Exception {
        ActionStart actionStart = new ActionStart(this);
        actionHandler.doAction(actionStart);
    }

    public void confirmFightOrder(String orderId, boolean confirm) throws Exception {
        ActionConfirmFightOrder action = new ActionConfirmFightOrder(orderId, confirm);
        actionHandler.doAction(action);
    }

    public List<CardAction> getCardAction(String cardId) throws Exception {
        var card = getPlayerCardById(cardId);
        var cardActions = card.getActions();
        return cardActions;
    }

    public void flushingEnd() throws Exception {
        IAction action = new ActionFlushingEnd();
        actionHandler.doAction(action);
    }

    public void flushingRoll() throws Exception {
        IAction action = new ActionFlushingRoll();
        actionHandler.doAction(action);
    }

    public void fightEnd() throws Exception {
        IAction action = new ActionFightEnd(getCurrentPlayer());
        actionHandler.doAction(action);
    }


    @JsonIgnore
    public List<Card> getAllCards() {

        // todo доделай add treasere card
        List<Card> allCards = new ArrayList<>();
        for (var card : getDoorCards())
            allCards.add(card);

        for (var card : getTreasureCards())
            allCards.add(card);

        for (var player : getPlayers())
            for (var card : player.getCards()) {
                allCards.add((Card) card);
                if (player.getArmorItemCard() != null)
                    allCards.add(player.getArmorItemCard());

                if (player.getHeadItemCard() != null)
                    allCards.add(player.getHeadItemCard());

                if (player.getLegsItemCard() != null)
                    allCards.add(player.getLegsItemCard());

                if (player.getWeaponItemCard_1() != null)
                    allCards.add(player.getWeaponItemCard_1());

                if (player.getWeaponItemCard_2() != null)
                    allCards.add(player.getWeaponItemCard_2());

                allCards.addAll(player.getBonusItemCards());
                allCards.addAll(player.getClasses());
                allCards.addAll(player.getRaces());
            }


        for (var move : getMoves()) {
            if (move.getFight() != null) {
                allCards.addAll(move.getFight().getHelpTreasureCards());
                allCards.addAll(move.getFight().getHarmTreasureCards());
                allCards.addAll(move.getFight().getEnemyCards());
                allCards.addAll(move.getFight().getDoorCards());
            }
        }

        return allCards;
    }

    @JsonIgnore
    public Card getPlayerCardById(String cardId) {

        var allCards = getAllCards();
        Optional<Card> foundObject = allCards.stream()
                .filter(obj -> obj.getId().equals(cardId))
                .findFirst();

        if (foundObject.isPresent())
            return foundObject.get();

        return null;
    }

    @JsonIgnore
    public Card getCardById(String cardId) {

        var allCards = getAllCards();
        Optional<Card> foundObject = allCards.stream()
                .filter(obj -> obj.getId().equals(cardId))
                .findFirst();

        if (foundObject.isPresent())
            return foundObject.get();


        for (var player : getPlayers()) {
            if (player.getArmorItemCard() != null)
                if (player.getArmorItemCard().getId().equalsIgnoreCase(cardId))
                    return player.getArmorItemCard();

            if (player.getHeadItemCard() != null)
                if (player.getHeadItemCard().getId().equalsIgnoreCase(cardId))
                    return player.getHeadItemCard();

            if (player.getLegsItemCard() != null)
                if (player.getLegsItemCard().getId().equalsIgnoreCase(cardId))
                    return player.getLegsItemCard();

            if (player.getWeaponItemCard_1() != null)
                if (player.getWeaponItemCard_1().getId().equalsIgnoreCase(cardId))
                    return player.getWeaponItemCard_1();

            if (player.getWeaponItemCard_2() != null)
                if (player.getWeaponItemCard_2().getId().equalsIgnoreCase(cardId))
                    return player.getWeaponItemCard_2();

        }


        return null;
    }

    @JsonIgnore
    public String kickDoor() throws Exception {
        var player = getPlayerByUser(getUser());
        ActionKickDoor actionKickDoor = new ActionKickDoor(player);
        actionHandler.doAction(actionKickDoor);
        return "";
    }

    @JsonIgnore
    public String createFightOrder(int treasureCount) throws Exception {
        var player = getCurrentPlayer();
        ActionCreateFightOrder actionKickDoor = new ActionCreateFightOrder(player, treasureCount);
        actionHandler.doAction(actionKickDoor);
        return "";
    }

    @JsonIgnore
    public List<OrderFight> getFightOrders() throws Exception {
        var player = getCurrentPlayer();
        var move = getLastMove();
        if (move == null)
            return null;

        var fight = move.getFight();
        if (fight == null)
            return null;

        var res = fight.getFightOrders();
        return res;
    }


    @JsonIgnore
    public List<IAction> getResolvedActions() throws Exception {
        var player = getPlayerByUser(getUser());

        return actionHandler.getResolvedActions(player);
    }


    public String playCard(PlayCardRequest playCardRequest) throws Exception {
        var user = getUser();
        if (user == null)
            throw new Exception("Not authorized");
        var player = getPlayerByUser(user);
        if (player == null)
            throw new Exception("User " + user.getUsername() + " is not connected to this game");

        var card = getPlayerCardById(playCardRequest.getCardId());
        if (card == null)
            throw new Exception("Not found card id " + playCardRequest.getCardId());

        var action = card.createAction(playCardRequest);

        actionHandler.doAction(action);

        return null;
    }

    public String transferCard(PlayCardRequest request) throws Exception {
        var user = getUser();
        if (user == null)
            throw new Exception("Not authorized");
        var player = getPlayerByUser(user);
        if (player == null)
            throw new Exception("User " + user.getUsername() + " is not connected to this game");

        var card = getPlayerCardById(request.getCardId());
        if (card == null)
            throw new Exception("Not found card id " + request.getCardId());

        var targetPlayer = getPlayerById(request.getPlayerId());
        var action = new ActionTransferCard(player, card, targetPlayer);

        actionHandler.doAction(action);

        return null;
    }

    public String sellCard(PlayCardRequest request) throws Exception {
        var user = getUser();
        if (user == null)
            throw new Exception("Not authorized");
        var player = getPlayerByUser(user);
        if (player == null)
            throw new Exception("User " + user.getUsername() + " is not connected to this game");

        var card = getPlayerCardById(request.getCardId());
        if (card == null)
            throw new Exception("Not found card id " + request.getCardId());

        var action = new ActionSellCard((TreasureCard) card, player);

        actionHandler.doAction(action);

        return null;
    }

    public String levelUp() throws Exception {
        var action = new ActionLevelUp(this);
        actionHandler.doAction(action);
        return null;
    }

    @JsonIgnore
    public DoorCard getRandomDoorCard() {
        return ListExtensions.extractRandom(getDoorCards());
    }

    @JsonIgnore
    public Move getLastMove() throws Exception {
        try {
            return moves.get(moves.size() - 1);
        } catch (Exception exc) {
            throw new Exception("Error getLastMove + " + exc.getMessage());
        }
    }

    @JsonIgnore
    public Fight getFight() throws Exception {
        try {
            var move = getLastMove();
            if (move.getFight() != null)
                return move.getFight();


            return null;

        } catch (Exception exc) {
            throw new Exception("Error getLastMove + " + exc.getMessage());
        }
    }


    // В любой момент игры ты можешь:
    //• сбросить класс или расу;
    //• сыграть карту «Получи уровень»;
    //• сыграть проклятие.
    @JsonIgnore
    public boolean isAll() throws Exception {
        if (!isGameStarted)
            return false;
        // throw new Exception("Game is not started");

        return true;
    }

    //В любой момент игры, когда ты нев бою, ты можешь:
    //• обменяться шмотками с другим игроком (он тоже не должен бытьв бою);
    //• «снять» одни шмотки, «надеть» другие;
    //• сыграть только что полученную карту (некоторые карты можно сыграть и в бою, см. выше).
    @JsonIgnore
    public boolean isAllNotFight() throws Exception {
        if (!isAll()) return false;

        var user = getUser();
        var move = getLastMove();
        if (move == null) return true;

        var fight = move.getFight();
        if (fight == null) return true;


        // Если этот игрок в бою
        if (fight.getPlayer().getUser().getUsername().equalsIgnoreCase(user.getUsername()) && !fight.isEnd())
            return false;


        var playerInOrder = fight.getFightOrders()
                .stream()
                .anyMatch(order ->
                        order.isTrust() && user.getUsername().equalsIgnoreCase(order.getPlayer().getUser().getUsername()));

        if (playerInOrder)
            return false;

        return true;
    }

    @JsonIgnore
    public void playRequired(String actionId) throws Exception {
        var action = getActionHandler().getRequiredActions()
                .stream().filter(x -> x.getId().equalsIgnoreCase(actionId))
                .findFirst();

        if (action.isPresent()) {
            getActionHandler().doAction(action.get());
        } else {
            throw new Exception("Not found required action id: " + actionId);
        }
    }

    @JsonIgnore
    public List<Card> getPlayerCards() {
        var user = getUser();
        var player = getPlayerByUser(user);
        return player.getCards();
    }

    //Только в свой ход ты можешь:
    //• сыграть новый класс или расу (в любой момент хода);
    //• продать шмотки за уровни (в любой момент хода, кроме боя);
    //• сыграть шмотку (большинство шмоток нельзя играть в бою, но некоторые разовые — можно, см.стр.6).
    @JsonIgnore
    public boolean isYourMove() throws Exception {
        if (!isAll()) return false;
        User user = getUser();

        var lastMove = getLastMove();
        if (lastMove != null) {
            if (lastMove.isEnd()) return false;

            if (lastMove.getPlayer().getUser().getUsername().equalsIgnoreCase(user.getUsername()))
                return true;
        }
        throw new Exception("It's not your turn now");
    }


    @Override
    public String toString() {
        try {
            String json = JSONSerializer.toJSON(this);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "context null";
    }


    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public List<DoorCard> getDoorCards() {
        return doorCards;
    }

    public void setDoorCards(List<DoorCard> doorCards) {
        this.doorCards = doorCards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public int getNeedPlayersForStart() {
        return needPlayersForStart;
    }

    public void setNeedPlayersForStart(int needPlayersForStart) {
        this.needPlayersForStart = needPlayersForStart;
    }

    @JsonIgnore
    public SimpMessagingTemplate getMessagingTemplate() {
        return messagingTemplate;
    }

    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @JsonIgnore
    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    public void setActionHandler(ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }


    public List<TreasureCard> getTreasureCards() {
        return treasureCards;
    }

    public void setTreasureCards(List<TreasureCard> treasureCards) {
        this.treasureCards = treasureCards;
    }

    public List<Card> getDiscardCards() {
        return discardCards;
    }

    public void setDiscardCards(List<Card> discardCards) {
        this.discardCards = discardCards;
    }

    public CardInit getCardInit() {
        return cardInit;
    }

    public void setCardInit(CardInit cardInit) {
        this.cardInit = cardInit;
    }

    public String cleaningNooks() throws Exception {
        var player = getPlayerByUser(getUser());
        var actionKickDoor = new ActionCleaningNooks(player);
        actionHandler.doAction(actionKickDoor);
        return "";
    }

    public String clericExile(String card1Id, String card2Id, String card3Id) throws Exception {

        var player = getCurrentPlayer();
        var cards = new ArrayList<Card>();
        var card1 = getCardById(card1Id);
        var card2 = getCardById(card2Id);
        var card3 = getCardById(card3Id);
        if (card1 != null)
            cards.add(card1);

        if (card2 != null)
            cards.add(card2);

        if (card3 != null)
            cards.add(card3);

        var actionClericExile = new ActionClericExile(cards, player);
        actionHandler.doAction(actionClericExile);
        return "";
    }

    public void halfingSell(String cardId) throws Exception {
        var player = getPlayerByUser(getUser());

        var card = getCardById(cardId);

        if (card instanceof TreasureCard) {
            var actionHalfingSell = new ActionHalfingSell(player, (TreasureCard) card);
            actionHandler.doAction(actionHalfingSell);
        }
    }

    public void halfingRoll(String cardId) throws Exception {
        var player = getPlayerByUser(getUser());
        var card = getCardById(cardId);
        var actionHalfingSell = new ActionHalfingRoll(player, card);
        actionHandler.doAction(actionHalfingSell);

    }

    public String wizardFly(String card1Id, String card2Id, String card3Id) throws Exception {
        var player = getPlayerByUser(getUser());
        var cards = new ArrayList<Card>();
        var card1 = getCardById(card1Id);
        var card2 = getCardById(card2Id);
        var card3 = getCardById(card3Id);
        if (card1 != null)
            cards.add(card1);

        if (card2 != null)
            cards.add(card2);

        if (card3 != null)
            cards.add(card3);

        var actionWizardFly = new ActionWizardFly(player, cards);
        actionHandler.doAction(actionWizardFly);
        return "";
    }


    public void wizardPacification(String cardId) throws Exception {

        var enemyCardOpt = getFight().getEnemyCards().stream()
                .filter(card -> card.getId().equalsIgnoreCase(cardId)).findFirst();
        if (enemyCardOpt.isPresent()) {
            var actionWizardPacification = new ActionWizardPacification(enemyCardOpt.get());
            actionHandler.doAction(actionWizardPacification);
        }
    }

    public String warriorRampage(String card1Id, String card2Id, String card3Id) throws Exception {
        var player = getCurrentPlayer();
        var cards = new ArrayList<Card>();
        var card1 = getCardById(card1Id);
        var card2 = getCardById(card2Id);
        var card3 = getCardById(card3Id);
        if (card1 != null)
            cards.add(card1);

        if (card2 != null)
            cards.add(card2);

        if (card3 != null)
            cards.add(card3);

        var actionWarriorRampage = new ActionWarriorRampage(cards, player);
        actionHandler.doAction(actionWarriorRampage);
        return "";
    }

    public String thiefCut(String cardId, String playerId) throws Exception {
        var player = getPlayerById(playerId);
        var card = getCardById(cardId);


        var actionWarriorRampage = new ActionThiefCut(player, card);
        actionHandler.doAction(actionWarriorRampage);
        return "";
    }

    public String thiefSteal(String cardId, String playerId) throws Exception {
        var player = getPlayerById(playerId);
        var card = getCardById(cardId);

        if (card instanceof ItemCard) {
            var actionWarriorRampage = new ActionThiefSteal(player, (ItemCard) card);
            actionHandler.doAction(actionWarriorRampage);
        }

        return "ok";
    }
}
