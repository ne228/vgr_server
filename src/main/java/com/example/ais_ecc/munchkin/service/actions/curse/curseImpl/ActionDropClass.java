package com.example.ais_ecc.munchkin.service.actions.curse.curseImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;

public class ActionDropClass extends RequiredAction {

    Player player;
    ClassesCard classesCard;

    public ActionDropClass(Player player, ClassesCard classesCard, String scopeId, MunchkinContext context) {
        this.classesCard = classesCard;
        this.player = player;

        setScopeId(scopeId);
        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Сбросить класс " + classesCard.getTitle();
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();
        if (!player.getId().equalsIgnoreCase(currentPlayer.getId()))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.getClasses().remove(classesCard);
        classesCard.discard(player);
        player.getCards().add(classesCard);
        context.discardCard(classesCard.getId());
        return "Игрок " + player.getUser().getUsername() + " сбросил класс " + classesCard.getTitle();
    }
}
