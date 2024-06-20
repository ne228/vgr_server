package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IRollAction;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Random;
import java.util.UUID;

public class ActionDropCubeOrcs extends RequiredAction implements IRollAction {

    private Player player;

    private RequiredAction nextAction;
    @JsonIgnore
    int roll = 0;


    public ActionDropCubeOrcs(Player player, String scopeId, MunchkinContext context) {
        this.player = player;
        this.scopeId = scopeId;
        setScopeId(scopeId);
        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Бросить кубик для непотребства от 3872 Орка";
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();
        if (!currentPlayer.getId().equalsIgnoreCase(player.getId()))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        Random random = new Random();
        var scopeId = UUID.randomUUID().toString();
        roll = random.nextInt(6) + 1;
        if (roll <= 2) {
            nextAction = new ActionAcceptDie(player, scopeId, context);
        } else {
            nextAction = new ActionAcceptLossLevel(player, roll, scopeId, context);
        }
        context.getActionHandler().addRequiredAction(nextAction);
        return "На кубике выпало " + roll;
    }

    @Override
    public void setRoll(int rollNum) {
        context.getActionHandler().getRequiredActions().removeAll(context.getActionHandler().getRequiredActions());
        if (rollNum <= 2) {
            nextAction = new ActionAcceptDie(player, scopeId, context);
        } else {
            nextAction = new ActionAcceptLossLevel(player, rollNum, scopeId, context);
        }
        try {
            context.getActionHandler().addRequiredAction(nextAction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @JsonIgnore
    @Override
    public int getRoll() {
        return roll;
    }
}
