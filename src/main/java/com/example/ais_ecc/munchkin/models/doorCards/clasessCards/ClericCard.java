package com.example.ais_ecc.munchkin.models.doorCards.clasessCards;

import com.example.ais_ecc.munchkin.models.classes.Classes;
import com.example.ais_ecc.munchkin.models.classes.ClericClass;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class ClericCard extends ClassesCard {
    public ClericCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Cleric";
        text = "<b>Воскрешение:</b> когда надо вытя" +
                "нуть карту лицом вверх, ты можешь" +
                "вместо этого взять верхнюю карту" +
                "из соответствующей кучи сброса. За" +
                "тем ты должен сбросить одну карту с" +
                "«руки».<br/>" +
                "<strong>Изгнание:</strong> можешь сбросить до З" +
                "карт в бою против Андедов. Каждый" +
                "сброс дает тебе +3 Бонус.";
        this.iconPath =  "/images/cleric.png";
    }


    @Override
    public Classes getClasses() {
        return new ClericClass();
    }
}
