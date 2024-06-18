package com.example.ais_ecc.munchkin.payload.response;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.service.JSONSerializer;

import java.util.List;

public class GetCardsResponse {

    public GetCardsResponse(List<Card> cards) {
        this.cards = cards;
    }
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
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
