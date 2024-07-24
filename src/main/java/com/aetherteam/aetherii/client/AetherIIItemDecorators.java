package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.IItemDecorator;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;

public class AetherIIItemDecorators {
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
        event.register(AetherIIItems.SKYROOT_SHORTSWORD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.SKYROOT_HAMMER, REINFORCED_DURABILITY);
        event.register(AetherIIItems.SKYROOT_SPEAR, REINFORCED_DURABILITY);
        event.register(AetherIIItems.SKYROOT_SHIELD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.HOLYSTONE_SHORTSWORD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.HOLYSTONE_HAMMER, REINFORCED_DURABILITY);
        event.register(AetherIIItems.HOLYSTONE_SPEAR, REINFORCED_DURABILITY);
        event.register(AetherIIItems.HOLYSTONE_SHIELD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ZANITE_SHORTSWORD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ZANITE_HAMMER, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ZANITE_SPEAR, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ZANITE_SHIELD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ARKENIUM_SHORTSWORD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ARKENIUM_HAMMER, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ARKENIUM_SPEAR, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ARKENIUM_SHIELD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.GRAVITITE_SHORTSWORD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.GRAVITITE_HAMMER, REINFORCED_DURABILITY);
        event.register(AetherIIItems.GRAVITITE_SPEAR, REINFORCED_DURABILITY);
        event.register(AetherIIItems.GRAVITITE_SHIELD, REINFORCED_DURABILITY);
        event.register(AetherIIItems.SKYROOT_AXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.HOLYSTONE_AXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ZANITE_AXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ARKENIUM_AXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.GRAVITITE_AXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.SKYROOT_PICKAXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.HOLYSTONE_PICKAXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ZANITE_PICKAXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ARKENIUM_PICKAXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.GRAVITITE_PICKAXE, REINFORCED_DURABILITY);
        event.register(AetherIIItems.SKYROOT_SHOVEL, REINFORCED_DURABILITY);
        event.register(AetherIIItems.HOLYSTONE_SHOVEL, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ZANITE_SHOVEL, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ARKENIUM_SHOVEL, REINFORCED_DURABILITY);
        event.register(AetherIIItems.GRAVITITE_SHOVEL, REINFORCED_DURABILITY);
        event.register(AetherIIItems.SKYROOT_TROWEL, REINFORCED_DURABILITY);
        event.register(AetherIIItems.HOLYSTONE_TROWEL, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ZANITE_TROWEL, REINFORCED_DURABILITY);
        event.register(AetherIIItems.ARKENIUM_TROWEL, REINFORCED_DURABILITY);
        event.register(AetherIIItems.GRAVITITE_TROWEL, REINFORCED_DURABILITY);
    }
}
