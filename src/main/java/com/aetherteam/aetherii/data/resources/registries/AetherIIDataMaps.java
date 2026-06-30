package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.maps.AmberHourglassFuel;
import com.aetherteam.aetherii.data.resources.maps.BlockInfection;
import com.aetherteam.aetherii.data.resources.maps.BucketReplacement;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.aetherteam.aetherii.util.RegistryObjectUtil.blockKey;
import static com.aetherteam.aetherii.util.RegistryObjectUtil.itemKey;

public final class AetherIIDataMaps {
    private static final Map<ResourceLocation, AmberHourglassFuel> AMBER_HOURGLASS_FUELS = new LinkedHashMap<>();
    private static final Map<ResourceLocation, BucketReplacement> BUCKET_REPLACEMENTS = new LinkedHashMap<>();
    private static final Map<ResourceLocation, BlockInfection> INFECTED_BLOCKS = new LinkedHashMap<>();

    static {
        AMBER_HOURGLASS_FUELS.put(AetherIIItems.GOLDEN_AMBER.getId(), new AmberHourglassFuel(400));
        AMBER_HOURGLASS_FUELS.put(AetherIIBlocks.GOLDEN_AMBER_BLOCK.getId(), new AmberHourglassFuel(4000));

        BUCKET_REPLACEMENTS.put(BuiltInRegistries.ITEM.getKey(Items.WATER_BUCKET), new BucketReplacement(itemKey(AetherIIItems.SKYROOT_WATER_BUCKET)));
        BUCKET_REPLACEMENTS.put(BuiltInRegistries.ITEM.getKey(Items.POWDER_SNOW_BUCKET), new BucketReplacement(itemKey(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET)));
        BUCKET_REPLACEMENTS.put(BuiltInRegistries.ITEM.getKey(Items.COD_BUCKET), new BucketReplacement(itemKey(AetherIIItems.SKYROOT_COD_BUCKET)));
        BUCKET_REPLACEMENTS.put(BuiltInRegistries.ITEM.getKey(Items.SALMON_BUCKET), new BucketReplacement(itemKey(AetherIIItems.SKYROOT_SALMON_BUCKET)));
        BUCKET_REPLACEMENTS.put(BuiltInRegistries.ITEM.getKey(Items.PUFFERFISH_BUCKET), new BucketReplacement(itemKey(AetherIIItems.SKYROOT_PUFFERFISH_BUCKET)));
        BUCKET_REPLACEMENTS.put(BuiltInRegistries.ITEM.getKey(Items.TROPICAL_FISH_BUCKET), new BucketReplacement(itemKey(AetherIIItems.SKYROOT_TROPICAL_FISH_BUCKET)));
        BUCKET_REPLACEMENTS.put(BuiltInRegistries.ITEM.getKey(Items.AXOLOTL_BUCKET), new BucketReplacement(itemKey(AetherIIItems.SKYROOT_AXOLOTL_BUCKET)));
        BUCKET_REPLACEMENTS.put(BuiltInRegistries.ITEM.getKey(Items.TADPOLE_BUCKET), new BucketReplacement(itemKey(AetherIIItems.SKYROOT_TADPOLE_BUCKET)));

        INFECTED_BLOCKS.put(AetherIIBlocks.GUARDIAN_LOG.getId(), new BlockInfection(blockKey(AetherIIBlocks.INFECTED_LOG)));
        INFECTED_BLOCKS.put(AetherIIBlocks.GUARDIAN_WOOD.getId(), new BlockInfection(blockKey(AetherIIBlocks.INFECTED_WOOD)));
        INFECTED_BLOCKS.put(AetherIIBlocks.STRIPPED_GUARDIAN_LOG.getId(), new BlockInfection(blockKey(AetherIIBlocks.STRIPPED_INFECTED_LOG)));
        INFECTED_BLOCKS.put(AetherIIBlocks.STRIPPED_GUARDIAN_WOOD.getId(), new BlockInfection(blockKey(AetherIIBlocks.STRIPPED_INFECTED_WOOD)));
    }

    private AetherIIDataMaps() {
    }

    public static AmberHourglassFuel getAmberHourglassFuel(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        return AMBER_HOURGLASS_FUELS.get(BuiltInRegistries.ITEM.getKey(stack.getItem()));
    }

    public static Stream<Item> amberHourglassFuelItems() {
        return AMBER_HOURGLASS_FUELS.keySet().stream().map(BuiltInRegistries.ITEM::get);
    }

    public static BucketReplacement getBucketReplacement(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        return BUCKET_REPLACEMENTS.get(BuiltInRegistries.ITEM.getKey(stack.getItem()));
    }

    public static BlockState infect(BlockState state) {
        BlockInfection infection = INFECTED_BLOCKS.get(BuiltInRegistries.BLOCK.getKey(state.getBlock()));
        if (infection != null) {
            Block block = BuiltInRegistries.BLOCK.get(infection.block().location());
            if (block != null) {
                return block.defaultBlockState();
            }
        }
        return state;
    }
}
