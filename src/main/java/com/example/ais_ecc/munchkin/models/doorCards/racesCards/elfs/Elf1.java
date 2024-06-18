package com.example.ais_ecc.munchkin.models.doorCards.racesCards.elfs;

import com.example.ais_ecc.munchkin.models.doorCards.racesCards.ElfCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class Elf1 extends ElfCard {
    public Elf1(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.iconPath = "/images/elf1.png";
    }
}
