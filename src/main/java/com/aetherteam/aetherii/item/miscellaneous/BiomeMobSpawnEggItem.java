package com.aetherteam.aetherii.item.miscellaneous;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.List;
import java.util.function.Supplier;

public class BiomeMobSpawnEggItem extends DeferredSpawnEggItem {
    private final String biome;

    public BiomeMobSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, String biome, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
        this.biome = biome;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("aether_ii." + this.biome).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
