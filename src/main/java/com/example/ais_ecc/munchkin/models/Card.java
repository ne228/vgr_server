package com.example.ais_ecc.munchkin.models;

//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;

import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionTransferCard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Card {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;

    @JsonIgnore
    protected MunchkinContext munchkinContext;
    protected String title;
    protected String subTitle;
    protected String text;
    public String iconPath = "/images/default.png";
    protected String ednpointPlayCard = "play_card";

    public Card(MunchkinContext munchkinContext) {
        this.munchkinContext = munchkinContext;
    }

    public abstract IAction createAction(PlayCardRequest playCardRequest) throws Exception;

    public List<CardAction> getActions() throws Exception {

        var currentPlayer = munchkinContext.getCurrentPlayer();

        // TRANSFER ACTIONS
        List<Player> anotherPlayerList = munchkinContext.getPlayers().stream()
                .filter(x -> x.getId() != currentPlayer.getId()).collect(Collectors.toList());

        var res = new ArrayList<CardAction>();

        // Если карта в сбросе - конец
        if (getMunchkinContext().getDiscardCards().stream().anyMatch(card -> getId().equalsIgnoreCase(card.getId())))
            return res;

        for (var player : anotherPlayerList) {

            PlayCardRequest playCardRequest = new PlayCardRequest(this.getId(), player.getId(), false, "null");
            var action = new ActionTransferCard(currentPlayer, this, player);
            try {
                if (!action.canAmI(munchkinContext))
                    return res;
            } catch (Exception e) {
                return res;
            }
            var text = action.getName();
            var path = playCardRequest.toEndpointPath(this.getEdnpointPlayCard(), munchkinContext.getId());
            path = path.replace("play_card", "transfer_card");
            var cardAction = new CardAction(path, text);

            res.add(cardAction);
        }

        return res;
    }

    public String getId() {
        if (id == null)
            setId(UUID.randomUUID().toString());
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconPath() {

        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEdnpointPlayCard() {
        return ednpointPlayCard;
    }

    public void setEdnpointPlayCard(String ednpointPlayCard) {
        this.ednpointPlayCard = ednpointPlayCard;
    }

    public MunchkinContext getMunchkinContext() {
        return munchkinContext;
    }

    public void setMunchkinContext(MunchkinContext munchkinContext) {
        this.munchkinContext = munchkinContext;
    }


}
