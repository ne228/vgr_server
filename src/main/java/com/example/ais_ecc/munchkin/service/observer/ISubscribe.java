package com.example.ais_ecc.munchkin.service.observer;

import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.UUID;

public abstract class ISubscribe {

    IAction action;
    public String id = UUID.randomUUID().toString();
    public ISubscribe(IAction action) {
        this.action = action;
    }
    public abstract void update();


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public IAction getAction() {
        return action;
    }

    public void setAction(IAction action) {
        this.action = action;
    }
}
