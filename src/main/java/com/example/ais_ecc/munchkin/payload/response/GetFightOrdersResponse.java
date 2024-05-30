package com.example.ais_ecc.munchkin.payload.response;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.OrderFight;
import com.example.ais_ecc.munchkin.service.JSONSerializer;

import java.util.List;

public class GetFightOrdersResponse {

    public GetFightOrdersResponse(List<OrderFight> fightOrders) {
        this.fightOrders = fightOrders;
    }
    private List<OrderFight> fightOrders;

    public List<OrderFight> getFightOrders() {
        return fightOrders;
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
