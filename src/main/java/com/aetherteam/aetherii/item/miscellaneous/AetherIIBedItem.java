package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.client.renderer.item.AetherIIBlockEntityItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BedBlock;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class AetherIIBedItem extends BedItem {
    public AetherIIBedItem(BedBlock block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return AetherIIBlockEntityItemRenderer.getInstance();
            }
        });
    }
}
