package com.aetherteam.aetherii.inventory.container;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class AccessoryContainer extends SimpleContainer implements INBTSerializable<ListTag> { //todo on world load the items get shuffled a bit for some reason.
    public AccessoryContainer(int size) {
        super(size);
    }

    @Override
    public ListTag serializeNBT(HolderLookup.Provider provider) {
        return this.createTag(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, ListTag tag) {
        this.fromTag(tag, provider);
    }
}
