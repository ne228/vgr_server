package com.example.ais_ecc.munchkin.models.classes;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.races.Races;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.List;

public abstract class Classes {
//    THIEF,
//    WIZARD,
//    CLERIC,
//    WARRIOR
    protected String name;
    public abstract void accept(Player player);
    public abstract void discard(Player player);

    public ClassList _class;
    public abstract List<IAction> getActions(Player player);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClassList get_class() {
        return _class;
    }

    public void set_class(ClassList _class) {
        this._class = _class;
    }
}
