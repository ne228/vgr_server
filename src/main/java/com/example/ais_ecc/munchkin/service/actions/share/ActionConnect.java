package com.example.ais_ecc.munchkin.service.actions.share;

import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionConnect extends IAction {

    private User user;


    public ActionConnect(MunchkinContext context) {
        this.path = "connect/" + context.getId();
        this.name = "Connect";
        this.title = "Connect";
        this.color = "success";
    }

    public ActionConnect(User user) {
        this.user = user;
    }

    public static ActionConnect createAction(MunchkinContext context) {
        return new ActionConnect(context);
    }


    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (context.isGameStarted)
            return false;

        return !munchkinContext.isConnected();
    }

    @Override
    public String start() throws Exception {
        for (Player player : getContext().players) {
            if (player.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
                throw new Exception("User already connected");
            }
        }
        Player player = new Player(user);
        getContext().players.add(player);
        return "User " + user.getUsername() + " success connected";
    }


}
