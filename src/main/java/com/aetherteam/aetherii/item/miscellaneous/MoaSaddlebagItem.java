package com.aetherteam.aetherii.item.miscellaneous;

import net.minecraft.world.item.Item;

public class MoaSaddlebagItem extends Item {
    private final int rowSize;

    public MoaSaddlebagItem(int rowSize, Properties properties) {
        super(properties);
        this.rowSize = rowSize;
    }

    public int getRowSize() {
        return this.rowSize;
    }

    public int getSize() {
        return this.getRowSize() * 3;
    }
}
