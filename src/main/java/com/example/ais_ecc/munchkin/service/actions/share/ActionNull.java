package com.example.ais_ecc.munchkin.service.actions.share;

import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionNull extends IAction {

    private String text;
    public ActionNull(String text) {
        this.text = text;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        return true;
    }

    @Override
    public String start() throws Exception {
        return text;
    }
}
