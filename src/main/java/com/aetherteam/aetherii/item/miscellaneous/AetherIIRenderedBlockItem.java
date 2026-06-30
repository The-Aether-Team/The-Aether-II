package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.client.renderer.item.AetherIIBlockEntityItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class AetherIIRenderedBlockItem extends BlockItem {
    public AetherIIRenderedBlockItem(Block block, Item.Properties properties) {
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
