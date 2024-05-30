package com.example.ais_ecc.munchkin.controllers;

import com.example.ais_ecc.munchkin.service.MunchkinContextHandler;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
//@CrossOrigin(origins = "*")
public class MunchkinSocket {


    final private MunchkinContextHandler contextHandler;

    public MunchkinSocket(MunchkinContextHandler contextHandler) {
        this.contextHandler = contextHandler;
    }

    @MessageMapping("game/{contextId}.munchkin.context")
    @SendTo("/topic/contexts/{contextId}")
    public String register(@Payload @DestinationVariable("contextId") String gameContextId, SimpMessageHeaderAccessor headerAccessor) {
        var gameContext = contextHandler.getContext(gameContextId);

        if (gameContext == null) {
            return gameContextId + " gameContext is not exist";
        }
        var res = gameContext.toString();

        return res;
    }

    @MessageMapping("game/{contextId}.munchkin.actions")
    @SendTo("/topic/actions/{contextId}")
    public String getActions(@Payload @DestinationVariable("contextId") String gameContextId, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        var gameContext = contextHandler.getContext(gameContextId);

        if (gameContext == null) {
            return gameContextId + " gameContext is not exist";
        }
        var res = gameContext.getResolvedActions().toString();

        return res;
    }


}