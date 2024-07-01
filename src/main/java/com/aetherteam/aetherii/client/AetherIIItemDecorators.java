package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.IItemDecorator;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;

public class AetherIIItemDecorators {
    private static final IItemDecorator REINFORCED_DURABILITY = (guiGraphics, font, stack, xOffset, yOffset) -> {
        guiGraphics.pose().pushPose();
        if (stack.isBarVisible() && stack.has(AetherIIDataComponents.REINFORCEMENT_TIER)) {
            int l = stack.getBarWidth();


            float stackMaxDamage = stack.getMaxDamage();
            float f = Math.max(0.0F, (stackMaxDamage - (float) stack.getDamageValue()) / stackMaxDamage);
            int i = Mth.hsvToRgb(f / 1.6F, 1.0F, 1.0F);


            int j = xOffset + 2;
            int k = yOffset + 13;
            guiGraphics.fill(RenderType.guiOverlay(), j, k, j + 13, k + 2, -16777216);
            guiGraphics.fill(RenderType.guiOverlay(), j, k, j + l, k + 1, i | 0xFF000000);
        }
        guiGraphics.pose().popPose();
        return true;
    };

    public static void registerItemDecorators(RegisterItemDecorationsEvent event) {
        event.register(AetherIIItems.SKYROOT_SHOVEL, REINFORCED_DURABILITY);
    }
}
