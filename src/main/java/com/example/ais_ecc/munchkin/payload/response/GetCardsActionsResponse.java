package com.example.ais_ecc.munchkin.payload.response;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.service.JSONSerializer;

import java.util.List;

public class GetCardsActionsResponse {

    public GetCardsActionsResponse(List<CardAction> cardActions) {
        this.cardActions = cardActions;
    }
    private List<CardAction> cardActions;

    public List<CardAction> getCardActions() {
        return cardActions;
    }

    @Override
    public String toString() {
        try {
            String json = JSONSerializer.toJSON(this);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "GetCardsResponse null";
    }
}
