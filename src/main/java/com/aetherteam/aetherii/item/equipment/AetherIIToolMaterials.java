package com.aetherteam.aetherii.item.equipment;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class AetherIIToolMaterials {
    public static final Tier SKYROOT = new ForgeTier(0, 59, 2.0F, 0.0F, 15, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(AetherIITags.Items.SKYROOT_REPAIRING));
    public static final Tier HOLYSTONE = new ForgeTier(1, 131, 4.0F, 1.0F, 5, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(AetherIITags.Items.HOLYSTONE_REPAIRING));
    public static final Tier ZANITE = new ForgeTier(2, 250, 6.0F, 2.0F, 14, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(AetherIITags.Items.ZANITE_REPAIRING));
    public static final Tier ARKENIUM = new ForgeTier(2, 1561, 6.0F, 2.0F, 14, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(AetherIITags.Items.ARKENIUM_REPAIRING));
    public static final Tier GRAVITITE = new ForgeTier(3, 1561, 8.0F, 3.0F, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(AetherIITags.Items.GRAVITITE_REPAIRING));

    public static final Tier HAMMER_OF_DEMOLITION = new ForgeTier(3, 1561, 6.0F, 2.0F, 15, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(AetherIITags.Items.HAMMER_OF_DEMOLITION_REPAIRING));
}
