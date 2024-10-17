package com.aetherteam.aetherii;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class AetherIIEnumExtensions {
    public static final EnumProxy<Rarity> AETHER_II_CURRENCY_RARITY_PROXY = new EnumProxy<>(
            Rarity.class, -1, "aether_ii:currency", (UnaryOperator<Style>) (style) -> AetherIIItems.CURRENCY_NAME_COLOR
    );

    public static final EnumProxy<Rarity> AETHER_II_TREASURE_RARITY_PROXY = new EnumProxy<>(
            Rarity.class, -1, "aether_ii:treasure", (UnaryOperator<Style>) (style) -> AetherIIItems.TREASURE_NAME_COLOR
    );

    public static Object altarSearchIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.COMPASS));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object altarFoodIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherIIItems.ENCHANTED_BLUEBERRY.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object altarBlocksIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherIIBlocks.QUICKSOIL_GLASS.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object altarRepairIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherIIItems.ZANITE_PICKAXE.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    public static Object altarMiscIcon(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(AetherIIItems.GRAVITITE_PLATE.get()));
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    private static String prefix(String id) {
        return AetherII.MODID + ":" + id;
    }
}