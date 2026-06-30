package com.aetherteam.aetherii.item.materials;

import java.util.List;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipDisplay;

import java.util.function.Consumer;

public class MoaFeatherItem extends Item {
    public MoaFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
        Moa.FeatherColor featherColor = AetherIIDataComponents.get(stack, AetherIIDataComponents.FEATHER_COLOR);
        if (featherColor != null) {
            tooltipComponents.add(Component.translatable("aether_ii.tooltip.item.moa_egg.feather_color." + featherColor.getSerializedName()).withStyle(ChatFormatting.GRAY));
        }
    }
}
