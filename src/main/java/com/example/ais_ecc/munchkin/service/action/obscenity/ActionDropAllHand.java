package com.example.ais_ecc.munchkin.service.action.obscenity;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.RequiredAction;

import java.util.ArrayList;
import java.util.List;

public class ActionDropAllHand extends RequiredAction {

    private Player player;

    public ActionDropAllHand(Player player, String scopeId, MunchkinContext context){
        setScopeId(scopeId);
        this.id = getId();
        this.player = player;
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Сбросить всю руку";
    }
    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();

        if (!currentPlayer.getId().equalsIgnoreCase(player.getId()))
            return false;


        return true;
    }

    @Override
    public String start() throws Exception {

        List<Card> cards = new ArrayList<>();
        cards.addAll(player.getCards());

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            context.discardCard(card.getId());
        }
        return "Игрок " + player.getUser().getUsername() + " сбросил все карты с руки";
    }
}
