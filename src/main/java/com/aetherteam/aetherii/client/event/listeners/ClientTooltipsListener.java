package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.hooks.ClientTooltipsHooks;
import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

import java.util.List;

public class ClientTooltipsListener {
    public static void listen(IEventBus bus) {
        bus.addListener(ClientTooltipsListener::gatherComponents);
    }

    public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack itemStack = event.getItemStack();
        List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
        ClientTooltipsHooks.addCharmTooltip(itemStack, tooltipElements);
    }
}
