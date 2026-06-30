package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.Holder;
import com.aetherteam.aetherii.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.IItemDecorator;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class AetherIIItemDecorators {
    public static final List<RegistryObject<? extends Item>> REINFORCEABLE = List.of(
            AetherIIItems.SKYROOT_SHORTSWORD,
            AetherIIItems.SKYROOT_HAMMER,
            AetherIIItems.SKYROOT_PIKE,
            AetherIIItems.HOLYSTONE_SHORTSWORD,
            AetherIIItems.HOLYSTONE_HAMMER,
            AetherIIItems.HOLYSTONE_PIKE,
            AetherIIItems.ZANITE_SHORTSWORD,
            AetherIIItems.ZANITE_HAMMER,
            AetherIIItems.ZANITE_PIKE,
            AetherIIItems.ARKENIUM_SHORTSWORD,
            AetherIIItems.ARKENIUM_HAMMER,
            AetherIIItems.ARKENIUM_PIKE,
            AetherIIItems.GRAVITITE_SHORTSWORD,
            AetherIIItems.GRAVITITE_HAMMER,
            AetherIIItems.GRAVITITE_PIKE,
            AetherIIItems.HAMMER_OF_DEMOLITION,
            AetherIIItems.SKYROOT_SHIELD,
            AetherIIItems.BURRUKAI_PLATE_SHIELD,
            AetherIIItems.ZANITE_SHIELD,
            AetherIIItems.ARKENIUM_SHIELD,
            AetherIIItems.GRAVITITE_SHIELD,
            AetherIIItems.SKYROOT_AXE,
            AetherIIItems.HOLYSTONE_AXE,
            AetherIIItems.ZANITE_AXE,
            AetherIIItems.ARKENIUM_AXE,
            AetherIIItems.GRAVITITE_AXE,
            AetherIIItems.SKYROOT_PICKAXE,
            AetherIIItems.HOLYSTONE_PICKAXE,
            AetherIIItems.ZANITE_PICKAXE,
            AetherIIItems.ARKENIUM_PICKAXE,
            AetherIIItems.GRAVITITE_PICKAXE,
            AetherIIItems.SKYROOT_SHOVEL,
            AetherIIItems.HOLYSTONE_SHOVEL,
            AetherIIItems.ZANITE_SHOVEL,
            AetherIIItems.ARKENIUM_SHOVEL,
            AetherIIItems.GRAVITITE_SHOVEL,
            AetherIIItems.SKYROOT_TROWEL,
            AetherIIItems.HOLYSTONE_TROWEL,
            AetherIIItems.ZANITE_TROWEL,
            AetherIIItems.ARKENIUM_TROWEL,
            AetherIIItems.GRAVITITE_TROWEL,
            AetherIIItems.BEAST_PELT_HELMET,
            AetherIIItems.BURRUKAI_PLATE_HELMET,
            AetherIIItems.ZANITE_HELMET,
            AetherIIItems.ARKENIUM_HELMET,
            AetherIIItems.GRAVITITE_HELMET,
            AetherIIItems.BEAST_PELT_CHESTPLATE,
            AetherIIItems.BURRUKAI_PLATE_CHESTPLATE,
            AetherIIItems.ZANITE_CHESTPLATE,
            AetherIIItems.ARKENIUM_CHESTPLATE,
            AetherIIItems.GRAVITITE_CHESTPLATE,
            AetherIIItems.BEAST_PELT_LEGGINGS,
            AetherIIItems.BURRUKAI_PLATE_LEGGINGS,
            AetherIIItems.ZANITE_LEGGINGS,
            AetherIIItems.ARKENIUM_LEGGINGS,
            AetherIIItems.GRAVITITE_LEGGINGS,
            AetherIIItems.BEAST_PELT_BOOTS,
            AetherIIItems.BURRUKAI_PLATE_BOOTS,
            AetherIIItems.ZANITE_BOOTS,
            AetherIIItems.ARKENIUM_BOOTS,
            AetherIIItems.GRAVITITE_BOOTS,
            AetherIIItems.BEAST_PELT_GLOVES,
            AetherIIItems.BURRUKAI_PLATE_GLOVES,
            AetherIIItems.ZANITE_GLOVES,
            AetherIIItems.ARKENIUM_GLOVES,
            AetherIIItems.GRAVITITE_GLOVES,
            AetherIIItems.ZANITE_PENDANT,
            AetherIIItems.ICESTONE_PENDANT);

    private static final IItemDecorator REINFORCED_DURABILITY = (guiGraphics, font, stack, xOffset, yOffset) -> {
        guiGraphics.pose().pushPose();
        if (stack.isBarVisible() && AetherIIDataComponents.has(stack, AetherIIDataComponents.REINFORCEMENT_TIER)) {
            int l = stack.getBarWidth();

            float stackMaxDamage = stack.getMaxDamage();
            float f = Mth.clamp((stackMaxDamage - (float) stack.getDamageValue()) / stackMaxDamage, 0.5F, 0.85F);
            int i = ARGB.setBrightness(0xFFD8D8D8, f);

            int j = xOffset + 2;
            int k = yOffset + 13;
            guiGraphics.fill(j, k, j + 13, k + 2, -16777216);
            guiGraphics.fill(j, k, j + l, k + 1, i | 0xFF000000);
        }
        guiGraphics.pose().popPose();
        return true;
    };

    public static void registerItemDecorators(RegisterItemDecorationsEvent event) {
        for (RegistryObject<? extends Item> item : REINFORCEABLE) {
            event.register(item.get(), REINFORCED_DURABILITY);
        }
    }
}
