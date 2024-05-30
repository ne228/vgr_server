package com.example.ais_ecc.munchkin.service.action;

import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class ActionDisconnect extends IAction {
    private User user;

    public ActionDisconnect(MunchkinContext context) {
        this.path = "disconnect/" + context.getId();
        this.name = "Disconnect";
        this.title = "Disconnect";
        this.color ="red";
    }

    public ActionDisconnect(User user) {
        this.user = user;
    }

    public static ActionDisconnect createAction(MunchkinContext context) {
        return new ActionDisconnect(context);
    }


    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;


        if (context.isGameStarted)
            return false;

        return munchkinContext.isConnected();

    }

    @Override
    public String start() throws Exception {
        for (Player player : context.getPlayers()) {
            if (player.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
                context.getPlayers().remove(player);
                return "User " + player.getUser().getUsername() + " success disconnected";
            }
        }
        throw new Exception("There is no such user");
    }

}
