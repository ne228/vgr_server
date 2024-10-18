package com.example.ais_ecc.munchkin.service.actions.curse.curseImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionDropLvl extends IAction {

    private final Player player;
    private final int lvlCount;

    public ActionDropLvl(Player player, int lvlCount) {
        this.player = player;
        this.lvlCount = lvlCount;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return true;
    }

    @Override
    public String start() throws Exception {
        player.lvlUp(-lvlCount);
        return "Игрок " + player.getUser().getUsername() + " потерял " + lvlCount + " уровней";
    }
}
