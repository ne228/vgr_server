package com.example.ais_ecc.munchkin.service.actions;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.payload.response.GetCardsResponse;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.fight.*;
import com.example.ais_ecc.munchkin.service.observer.SubscribeService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;


public class ActionHandler {

    private MunchkinContext munchkinContext;
    private SimpMessagingTemplate messagingTemplate;

    private SubscribeService subscribeService;

    private ArrayList<IAction> actions;

    private ArrayList<IAction> endActions;

    private ArrayList<RequiredAction> requiredActions;

    public ActionHandler(MunchkinContext munchkinContext,
                         SimpMessagingTemplate messagingTemplate) throws Exception {
        this.munchkinContext = munchkinContext;
        this.messagingTemplate = messagingTemplate;
        this.subscribeService = new SubscribeService();
        actions = new ArrayList<>();
        endActions = new ArrayList<>();
        requiredActions = new ArrayList<>();

        // SHARE ACTIONS
        actions.add(ActionKickDoor.createAction(munchkinContext));
        actions.add(ActionConnect.createAction(munchkinContext));
        actions.add(ActionDisconnect.createAction(munchkinContext));
        actions.add(ActionStart.createAction(munchkinContext));
        actions.add(ActionLevelUp.createAction(munchkinContext));
        actions.add(ActionCleaningNooks.createAction(munchkinContext));

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


        // RACES ACTIONS
        // TODO
//        if (player != null)
//            for (var race : player.getRaces())
//                for (var action : race.getActions())
//                    actions.add(action);
    }

    public void updateContext() {
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

            // Поиск по id обязательный действий
            var reqActionOpt = requiredActions.stream()
                    .filter(act -> act.getId().equalsIgnoreCase(action.getId()))
                    .findFirst();

            // Проверяем обязательное ли действия
            // Если да выполняем по правилу обязательных действий
            // Удаляем все из scope обязательныйх действий
            if (reqActionOpt.isPresent()) {
                var reqAct = reqActionOpt.get();
                var scopedActions = new ArrayList<RequiredAction>();
                for (var act : requiredActions)
                    if (act.getScopeId().equalsIgnoreCase(reqAct.getScopeId()))
                        scopedActions.add(act);
//                var scopedActions = requiredActions.stream()
//                        .filter(act -> act.getScopeId().equalsIgnoreCase(reqAct.getScopeId()))
//                        .collect(Collectors.toList());

                requiredActions.removeAll(scopedActions);

            } else { // Если не обязательное действи, проверяем выполнены ли все обязтаельные действия
                if (requiredActions.size() > 0)
                    return;
            }


            var resultAction = action.start();
            endActions.add(action);
            munchkinContext.getMessages().add(resultAction);

            subscribeService.afterUpdate(action);
            updateContext();
            updatePlayerActions();
            updateCards();
        }
    }

    public void doRawAction(IAction action) throws Exception {
        var resultAction = action.start();
        endActions.add(action);
        munchkinContext.getMessages().add(resultAction);
        updateContext();
        updatePlayerActions();
        updateCards();

    }

    public void addRequiredAction(RequiredAction action) throws Exception {
        getRequiredActions().add(action);
        updateContext();
        updatePlayerActions();
        updateCards();
    }

    public ArrayList<IAction> getResolvedActions(Player player) throws Exception {
        var res = new ArrayList<IAction>();

        // Пока не выполнены все обязательный действия - нельзя продолжить игру
        for (IAction requiredAction : requiredActions) {
            if (requiredAction.canAmI(munchkinContext))
                res.add(requiredAction);
        }

        if (requiredActions.size() > 0)
            return res;

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

        // CLASSES ACTIONS
        // TODO
        var classesActions = new ArrayList<IAction>();
        for (var _class : player.getClasses())
            classesActions.addAll(_class.getClassActions());

        for (IAction action : classesActions) {
            try {
                var canAmi = action.canAmI(munchkinContext);
                if (canAmi)
                    res.add(action);
            } catch (Exception exc) {
                // Nothing do
            }
        }

// TODO
//        for (Races race : player.getRaces()) {
//            for (IAction action : race.getActions(player)) {
//                try {
//                    if (action.canAmI(munchkinContext))
//                        res.add(action);
//                } catch (Exception exc) {
//                    // Nothing do
//                }
//            }
//        }

        return res;
    }


    public MunchkinContext getMunchkinContext() {
        return munchkinContext;
    }

    public void setMunchkinContext(MunchkinContext munchkinContext) {
        this.munchkinContext = munchkinContext;
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

    public ArrayList<RequiredAction> getRequiredActions() {
        return requiredActions;
    }

    public void setRequiredActions(ArrayList<RequiredAction> requiredActions) {
        this.requiredActions = requiredActions;
    }

    public SimpMessagingTemplate getMessagingTemplate() {
        return messagingTemplate;
    }

    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public ArrayList<IAction> getEndActions() {
        return endActions;
    }

    public void setEndActions(ArrayList<IAction> endActions) {
        this.endActions = endActions;
    }
}
