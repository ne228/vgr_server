package com.example.ais_ecc.munchkin.service;

import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Scope("singleton")
public class MunchkinContextHandler {

    public final UserRepository userRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private List<MunchkinContext> contextList;

    public MunchkinContextHandler(UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
        this.contextList = new ArrayList<>();
    }


    @JsonIgnore
    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, аутентифицирован ли пользователь и является ли объект Principal объектом User
        if (authentication != null) {
            // Получаем объект User

            User user = (User) authentication.getPrincipal();
//            System.out.println("Текущий пользователь: " + user.getUsername());
            return user;
        } else {
            System.out.println("Пользователь не аутентифицирован или не представлен объект User");
        }
        return null;
    }

    public MunchkinContext createGame() throws Exception {
        var context = new MunchkinContext();

        context.setId(UUID.randomUUID().toString());
        context.registerServices(
                messagingTemplate,
                userRepository);
        context.addPlayer(getUser());
        getContextList().add(context);
        return context;
    }

    public MunchkinContext getContext(String id) {
        for (var context : getContextList())
            if (context.getId().equalsIgnoreCase(id))
                return context;
        return null;
    }

    public List<MunchkinContext> getContextsByUser(User user) {
        List<MunchkinContext> result = new ArrayList<>();
        for (var context : getContextList())
            for (var player : context.getPlayers()) {
                var allUsername = player.getUser().getUsername();
                var targetUserName = user.getUsername();
                if (allUsername.equalsIgnoreCase(targetUserName)) {
                    result.add(context);
                    continue;
                }
            }
        return result;
    }

    public List<MunchkinContext> getContextList() {
        return contextList;
    }

    public void setContextList(List<MunchkinContext> contextList) {
        this.contextList = contextList;
    }
}
