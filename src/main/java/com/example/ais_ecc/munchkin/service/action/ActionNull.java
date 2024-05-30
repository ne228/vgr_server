package com.example.ais_ecc.munchkin.service.action;

import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class ActionNull extends IAction{

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
