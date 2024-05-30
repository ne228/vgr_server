package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionPlayClasses extends IAction {

    String nameClass;
    private Player player;
    private ClassesCard classesCard;
    private Move move;
    private MunchkinContext context;

    public ActionPlayClasses(Player player, ClassesCard classesCard) {
        this.player = player;
        this.classesCard = classesCard;
    }

    private ActionPlayClasses() {
        this.path = "playClass";
        this.name = "Play class";
        this.title = "Play class";
    }

    public static ActionPlayClasses createAction() {
        return new ActionPlayClasses();
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        nameClass = classesCard.getClasses().getName();
        this.name = "Play " + nameClass;
        this.title = "Play " + nameClass;

        if (!context.isAllNotFight())
            return false;

            if (player.getClasses().size() > 0)
            return false;

        var player = context.getCurrentPlayer();
        var playerHaveTargetCard = player
                .getCards().stream()
                .anyMatch(card -> card.getId().equalsIgnoreCase(classesCard.getId()));
        if (!playerHaveTargetCard)
            return false;


        return true;
    }

    @Override
    public String start() throws Exception {
        player.getClasses().add(classesCard.getClasses());
        classesCard.getClasses().accept(player);
        context.discardCard(classesCard.getId());

        return "Player " + player.getUser().getUsername() + " is class + " + nameClass;   //TODO
    }
}
