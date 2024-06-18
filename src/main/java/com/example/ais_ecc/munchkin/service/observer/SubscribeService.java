package com.example.ais_ecc.munchkin.service.observer;

import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.ArrayList;
import java.util.List;

public class SubscribeService {
    public List<ISubscribe> subscribers = new ArrayList<>();

    public void register(ISubscribe subscribe) {
        subscribers.add(subscribe);
    }

    public void unRegister(ISubscribe subscribe) {
        for (int i = 0; i < subscribers.size(); i++) {
            var _subscribe = subscribers.get(i);
            if (_subscribe.getId().equalsIgnoreCase(subscribe.getId())) {
                subscribers.remove(_subscribe);
                return;
            }
        }
    }

    public void update(IAction action){
        for (int i = 0; i < subscribers.size(); i++) {
            var _subscribe = subscribers.get(i);
            if (_subscribe.getAction().getClass() == action.getClass())
                _subscribe.update();
        }
    }
}
