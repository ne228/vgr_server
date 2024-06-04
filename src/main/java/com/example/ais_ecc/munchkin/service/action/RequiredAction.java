package com.example.ais_ecc.munchkin.service.action;

import com.example.ais_ecc.munchkin.service.MunchkinContext;

public abstract class RequiredAction extends IAction{

    public String scopeId;

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

}
