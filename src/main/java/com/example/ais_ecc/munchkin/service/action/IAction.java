package com.example.ais_ecc.munchkin.service.action;

import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public abstract class IAction {
    public String id;
    protected String name;
    protected String path;
    protected String color = "red";
    protected String title;
    @JsonIgnore
    protected MunchkinContext context;



    public abstract boolean canAmI(MunchkinContext munchkinContext) throws Exception;
    public abstract String start() throws Exception;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MunchkinContext getContext() {
        return context;
    }

    public void setContext(MunchkinContext context) {
        this.context = context;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        if (id == null)
            setId(UUID.randomUUID().toString());
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
