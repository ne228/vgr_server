package com.example.ais_ecc.munchkin.payload.response;

import com.example.ais_ecc.munchkin.service.JSONSerializer;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class GetActionsResponse {

    private final List<IAction> actions;

    @JsonIgnore
    public GetActionsResponse(List<IAction> actions) {
        this.actions = actions;
    }

    public List<IAction> getActions() {
        return actions;
    }
    @Override
    public String toString() {
        try {
            String json = JSONSerializer.toJSON(this);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "GetActionsResponse null";
    }
}
