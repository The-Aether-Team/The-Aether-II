package com.aetherteam.aetherii.item.miscellaneous;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class BiomeMobSpawnEggItem extends SpawnEggItem {
    private final String biome;

    public BiomeMobSpawnEggItem(String biome, Properties props) {
        super(props);
        this.biome = biome;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.accept(Component.translatable("aether_ii." + this.biome).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
