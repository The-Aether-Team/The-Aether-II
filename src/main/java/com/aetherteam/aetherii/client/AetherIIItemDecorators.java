package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.miscellaneous.GliderItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.IItemDecorator;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;

public class AetherIIItemDecorators {
    private static final IItemDecorator GLIDER_DURATION = (guiGraphics, font, stack, xOffset, yOffset) -> {
        guiGraphics.pose().pushPose();
        if (stack.getItem() instanceof GliderItem) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                int max = GliderItem.GLIDING_MAX;
                int progress = GliderItem.getGlidingTimer(stack);
                if (progress > 0 && progress < max) {
                    int barWidth = Math.round((float) progress * 13.0F / (float) max);
                    int rgb = 3183871;

                    int j = xOffset + 2;
                    int k = yOffset + 11;
                    guiGraphics.fill(RenderType.guiOverlay(), j, k, j + 13, k + 2, -16777216);
                    guiGraphics.fill(RenderType.guiOverlay(), j, k, j + barWidth, k + 1, rgb | 0xFF000000);
                }
            }
        }
        guiGraphics.pose().popPose();
        return true;
    };

    private static final IItemDecorator REINFORCED_DURABILITY = (guiGraphics, font, stack, xOffset, yOffset) -> { //todo improve visuals
        guiGraphics.pose().pushPose();
        if (stack.isBarVisible() && stack.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
            int l = stack.getBarWidth();


            float stackMaxDamage = stack.getMaxDamage();
            float f = Math.max(0.0F, (stackMaxDamage - (float) stack.getDamageValue()) / stackMaxDamage);
            int i = Mth.hsvToRgb(f / 3.0F, 0.0F, 0.85F);


            int j = xOffset + 2;
            int k = yOffset + 13;
            guiGraphics.fill(RenderType.guiOverlay(), j, k, j + 13, k + 2, -16777216);
            guiGraphics.fill(RenderType.guiOverlay(), j, k, j + l, k + 1, i | 0xFF000000);
        }
        guiGraphics.pose().popPose();
        return true;
    };

    public static void registerItemDecorators(RegisterItemDecorationsEvent event) {
        event.register(AetherIIItems.COLD_AERCLOUD_GLIDER, GLIDER_DURATION);
        event.register(AetherIIItems.GOLDEN_AERCLOUD_GLIDER, GLIDER_DURATION);

        for (Holder<Item> item : ArkeniumForgeMenu.REINFORCEABLE) {
            event.register(item.value(), REINFORCED_DURABILITY);
        }
    }
}
