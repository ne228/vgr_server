package com.example.ais_ecc.munchkin.service.action;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.classes.Classes;
import com.example.ais_ecc.munchkin.models.races.Races;
import com.example.ais_ecc.munchkin.payload.response.GetCardsResponse;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.fight.*;
import com.example.ais_ecc.munchkin.service.observer.SubscribeService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;


public class ActionHandler {

    private MunchkinContext munchkinContext;
    private SimpMessagingTemplate messagingTemplate;

    private SubscribeService subscribeService;

    private ArrayList<IAction> actions;

    public ActionHandler(MunchkinContext munchkinContext,
                         SimpMessagingTemplate messagingTemplate) throws Exception {
        this.munchkinContext = munchkinContext;
        this.messagingTemplate = messagingTemplate;
        this.subscribeService = new SubscribeService();
        actions = new ArrayList<>();

        // SHARE ACTIONS
        actions.add(ActionKickDoor.createAction(munchkinContext));
        actions.add(ActionConnect.createAction(munchkinContext));
        actions.add(ActionDisconnect.createAction(munchkinContext));
        actions.add(ActionStart.createAction(munchkinContext));
        actions.add(ActionLevelUp.createAction(munchkinContext));

        // FIGHT ACTIONS
        actions.add(ActionFightAgree.createAction(munchkinContext));
        actions.add(ActionFightEnd.createAction(munchkinContext));
        actions.add(ActionCreateFightOrder.createAction(munchkinContext));
        actions.add(ActionConfirmFightOrder.createAction(munchkinContext));

        // Flushing
        actions.add(ActionFlushingEnd.createAction(munchkinContext));
        actions.add(ActionFlushingRoll.createAction(munchkinContext));

        // cards
//        actions.add(ActionPlayCurse.createAction());


        // CLASSES ACTIONS
        var player = munchkinContext.getCurrentPlayer();
        if (player != null)
            for (Classes _class : player.getClasses())
                for (var action : _class.getActions(player))
                    actions.add(action);


        // RACES ACTIONS
        if (player != null)
            for (Races race : player.getRaces())
                for (var action : race.getActions(player))
                    actions.add(action);
    }

    private void updateContext() {
        var contextId = munchkinContext.getId();
        if (contextId == null) {
            messagingTemplate.convertAndSend("/topic/contexts", munchkinContext.getId() + " gameContext is null");
            return;
        }
        messagingTemplate.convertAndSend("/topic/contexts/" + contextId, munchkinContext);
    }

    private void updatePlayerActions() throws Exception {
        var contextId = munchkinContext.getId();
        var resolvedActions = munchkinContext.getResolvedActions();
        messagingTemplate.convertAndSend("/topic/actions/" + contextId, resolvedActions);
    }

    private void updateCards() throws Exception {
        var cards = munchkinContext.getPlayerCards();
        var response = new GetCardsResponse(cards);
        messagingTemplate.convertAndSend("/topic/cards/" + munchkinContext.getId(), response.toString());
    }

    public void doAction(IAction action) throws Exception {
        if (action.canAmI(munchkinContext)) {

            subscribeService.update(action);
            var resultAction = action.start();
            munchkinContext.getMessages().add(resultAction);
            updateContext();
            updatePlayerActions();
            updateCards();
        }
    }

    public ArrayList<IAction> getResolvedActions(Player player) throws Exception {
        var res = new ArrayList<IAction>();

        var currentPlayer = munchkinContext.getCurrentPlayer();
        for (IAction action : getActions()) {
            try {
                var canAmi = action.canAmI(munchkinContext);
                if (canAmi)
                    res.add(action);
            } catch (Exception exc) {
                // Nothing do
            }
        }
        if (player == null)
            return res;

        for (Classes _class : player.getClasses()) {
            for (IAction action : _class.getActions(player)) {
                try {
                    if (action.canAmI(munchkinContext))
                        res.add(action);
                } catch (Exception exc) {
                    // Nothing do
                }
            }
        }


        for (Races race : player.getRaces()) {
            for (IAction action : race.getActions(player)) {
                try {
                    if (action.canAmI(munchkinContext))
                        res.add(action);
                } catch (Exception exc) {
                    // Nothing do
                }
            }
        }

        return res;
    }


    public MunchkinContext getMunchkinContext() {
        return munchkinContext;
    }

    public ArrayList<IAction> getActions() {
        return actions;
    }

    public void setActions(ArrayList<IAction> actions) {
        this.actions = actions;
    }

    public SubscribeService getSubscribeService() {
        return subscribeService;
    }

    public void setSubscribeService(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }
}
