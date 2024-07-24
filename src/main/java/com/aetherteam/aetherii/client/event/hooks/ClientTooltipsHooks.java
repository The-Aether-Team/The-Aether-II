package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.client.renderer.item.tooltip.ClientCharmTooltip;
import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class ClientTooltipsHooks {
    public static void addCharmTooltip(ItemStack itemStack, List<Either<FormattedText, TooltipComponent>> tooltipElements) {
        Component id = Component.literal(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString()).withStyle(ChatFormatting.DARK_GRAY);
        int componentIndex = tooltipElements.size() - 1;

        for (int i = 0; i < tooltipElements.size(); i++) {
            Either<FormattedText, TooltipComponent> tooltips = tooltipElements.get(i);
            Optional<FormattedText> text = tooltips.left();
            if (text.isPresent() && text.get().getString().equals(id.getString())) {
                componentIndex = i;
            }
        }
        List<ItemStack> charms = itemStack.get(AetherIIDataComponents.CHARMS);
        if (charms != null) {
            tooltipElements.add(componentIndex, Either.right(new ClientCharmTooltip.CharmTooltip(itemStack, charms)));
        }
    }
}
