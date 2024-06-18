package com.example.ais_ecc.munchkin.service.actions;

public abstract class RequiredAction extends IAction{

    public String scopeId;

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

}
