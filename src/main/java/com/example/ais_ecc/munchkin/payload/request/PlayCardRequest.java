package com.example.ais_ecc.munchkin.payload.request;

public class PlayCardRequest {
    private String cardId;
    private String playerId;
    private Boolean isHarm;
    private String targetCardId;

    public PlayCardRequest(String cardId, String playerId, Boolean isHarm, String targetCardId) {
        this.cardId = cardId;
        this.playerId = playerId;
        this.isHarm = isHarm;
        this.targetCardId = targetCardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public boolean isHarm() {
        return isHarm;
    }

    public void setHarm(boolean harm) {
        isHarm = harm;
    }

    public String getTargetCardId() {
        return targetCardId;
    }

    public void setTargetCardId(String targetCardId) {
        this.targetCardId = targetCardId;
    }

    public String toEndpointPath(String endpoint, String contextId) {
        var req = "/" + endpoint + "/" + contextId;
        req += toString();
        return req;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        if (cardId != null)
            stringBuilder.append("cardId=").append(cardId).append("&");
        else
            stringBuilder.append("cardId=").append("null").append("&");

        if (playerId != null)
            stringBuilder.append("playerId=").append(playerId).append("&");
        else
            stringBuilder.append("playerId=").append("null").append("&");

        if (isHarm != null)
            stringBuilder.append("isHarm=").append(isHarm).append("&");
        else
            stringBuilder.append("isHarm=").append(false).append("&");

        if (targetCardId != null)
            stringBuilder.append("targetCardId=").append(targetCardId).append("&");
        else
            stringBuilder.append("targetCardId=").append("null").append("&");

        // Удаляем последний символ "&" если он есть
        if (stringBuilder.length() > 1 && stringBuilder.charAt(stringBuilder.length() - 1) == '&') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
