package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.api.styles.StyleMaterial;
import com.aetherteam.aetherii.api.styles.StyleDesign;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record ArmorStyle(Holder<StyleMaterial> material, Holder<StyleDesign> style) implements TooltipProvider {


    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {

    }
}
