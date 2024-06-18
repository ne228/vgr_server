package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionTakeOffClass extends IAction {

    String nameRace;
    private Player player;
    private ClassesCard classesCard;
    private Move move;
    private MunchkinContext context;

    public ActionTakeOffClass(Player player, ClassesCard classesCard) {
        this.player = player;
        this.classesCard = classesCard;
    }


    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        nameRace = classesCard.getTitle();
        this.name = "Сбросить  " + nameRace;
        this.title = "Сбросить  " + nameRace;

        if (!context.isAllNotFight())
            return false;

        if (!player.isClass(classesCard.get_class()))
            return false;

        if (player.getClasses().stream().noneMatch(card -> card.getId().equalsIgnoreCase(classesCard.getId())))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.getClasses().remove(classesCard);
        classesCard.discard(player);
        player.getCards().add(classesCard);
        return "Игрок " + player.getUser().getUsername() + "сбросил класс  " + nameRace;   //TODO
    }
}
