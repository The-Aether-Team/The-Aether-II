package com.aetherteam.aetherii.item.materials;

import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class MoaFeatherItem extends Item {
    public MoaFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipComponents, tooltipFlag);
        Moa.FeatherColor featherColor = stack.get(AetherIIDataComponents.FEATHER_COLOR);
        if (featherColor != null) {
            tooltipComponents.accept(Component.translatable("aether_ii.tooltip.item.moa_egg.feather_color." + featherColor.getSerializedName()).withStyle(ChatFormatting.GRAY));
        }
    }
}
