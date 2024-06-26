package com.aetherteam.aetherii;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AetherIIEnumExtensions {
    public static Object altarSearchIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> prefix("altar_search");
            case 1 -> new ItemStack(Items.COMPASS);
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object altarFoodIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> prefix("altar_food");
            case 1 -> new ItemStack(AetherIIItems.ENCHANTED_BLUEBERRY.get());
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object altarBlocksIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> prefix("altar_blocks");
            case 1 -> new ItemStack(AetherIIBlocks.QUICKSOIL_GLASS.get());
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object altarRepairIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> prefix("altar_repair");
            case 1 -> new ItemStack(AetherIIItems.ZANITE_PICKAXE.get());
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object altarMiscIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> prefix("altar_misc");
            case 1 -> new ItemStack(AetherIIItems.GRAVITITE_PLATE.get());
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    private static String prefix(String id) {
        return AetherII.MODID + ":" + id;
    }
}
