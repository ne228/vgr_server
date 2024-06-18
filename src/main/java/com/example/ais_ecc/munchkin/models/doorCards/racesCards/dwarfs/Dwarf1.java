package com.example.ais_ecc.munchkin.models.doorCards.racesCards.dwarfs;

import com.example.ais_ecc.munchkin.models.doorCards.racesCards.DwarfCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class Dwarf1 extends DwarfCard {
    public Dwarf1(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.iconPath = "/images/dwarf1.png";
    }
}
