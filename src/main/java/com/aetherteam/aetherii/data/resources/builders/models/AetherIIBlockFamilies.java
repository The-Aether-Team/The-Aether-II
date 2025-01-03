package com.aetherteam.aetherii.data.resources.builders.models;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class AetherIIBlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    public static final BlockFamily SKYROOT_PLANKS = familyBuilder(AetherIIBlocks.SKYROOT_PLANKS.get())
            .button(AetherIIBlocks.SKYROOT_BUTTON.get())
            .fence(AetherIIBlocks.SKYROOT_FENCE.get())
            .fenceGate(AetherIIBlocks.SKYROOT_FENCE_GATE.get())
            .pressurePlate(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get())
            .sign(AetherIIBlocks.SKYROOT_SIGN.get(), AetherIIBlocks.SKYROOT_WALL_SIGN.get())
            .slab(AetherIIBlocks.SKYROOT_SLAB.get())
            .stairs(AetherIIBlocks.SKYROOT_STAIRS.get())
            .door(AetherIIBlocks.SKYROOT_DOOR.get())
            .trapdoor(AetherIIBlocks.SKYROOT_TRAPDOOR.get())
            .getFamily();
    public static final BlockFamily GREATROOT_PLANKS = familyBuilder(AetherIIBlocks.GREATROOT_PLANKS.get())
            .button(AetherIIBlocks.GREATROOT_BUTTON.get())
            .fence(AetherIIBlocks.GREATROOT_FENCE.get())
            .fenceGate(AetherIIBlocks.GREATROOT_FENCE_GATE.get())
            .pressurePlate(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get())
            .sign(AetherIIBlocks.GREATROOT_SIGN.get(), AetherIIBlocks.GREATROOT_WALL_SIGN.get())
            .slab(AetherIIBlocks.GREATROOT_SLAB.get())
            .stairs(AetherIIBlocks.GREATROOT_STAIRS.get())
            .door(AetherIIBlocks.GREATROOT_DOOR.get())
            .trapdoor(AetherIIBlocks.GREATROOT_TRAPDOOR.get())
            .getFamily();
    public static final BlockFamily WISPROOT_PLANKS = familyBuilder(AetherIIBlocks.WISPROOT_PLANKS.get())
            .button(AetherIIBlocks.WISPROOT_BUTTON.get())
            .fence(AetherIIBlocks.WISPROOT_FENCE.get())
            .fenceGate(AetherIIBlocks.WISPROOT_FENCE_GATE.get())
            .pressurePlate(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get())
            .sign(AetherIIBlocks.WISPROOT_SIGN.get(), AetherIIBlocks.WISPROOT_WALL_SIGN.get())
            .slab(AetherIIBlocks.WISPROOT_SLAB.get())
            .stairs(AetherIIBlocks.WISPROOT_STAIRS.get())
            .door(AetherIIBlocks.WISPROOT_DOOR.get())
            .trapdoor(AetherIIBlocks.WISPROOT_TRAPDOOR.get())
            .getFamily();
    public static final BlockFamily HOLYSTONE = familyBuilder(AetherIIBlocks.HOLYSTONE.get())
            .button(AetherIIBlocks.HOLYSTONE_BUTTON.get())
            .pressurePlate(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get())
            .wall(AetherIIBlocks.HOLYSTONE_WALL.get())
            .slab(AetherIIBlocks.HOLYSTONE_SLAB.get())
            .stairs(AetherIIBlocks.HOLYSTONE_STAIRS.get())
            .getFamily();
    public static final BlockFamily MOSSY_HOLYSTONE = familyBuilder(AetherIIBlocks.MOSSY_HOLYSTONE.get())
            .wall(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get())
            .slab(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get())
            .stairs(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get())
            .getFamily();
    public static final BlockFamily IRRADIATED_HOLYSTONE = familyBuilder(AetherIIBlocks.IRRADIATED_HOLYSTONE.get())
            .wall(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get())
            .slab(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get())
            .stairs(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get())
            .getFamily();
    public static final BlockFamily HOLYSTONE_BRICKS = familyBuilder(AetherIIBlocks.HOLYSTONE_BRICKS.get())
            .wall(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get())
            .slab(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get())
            .stairs(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get())
            .getFamily();
    public static final BlockFamily FADED_HOLYSTONE_BRICKS = familyBuilder(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get())
            .wall(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get())
            .slab(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get())
            .stairs(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get())
            .getFamily();
    public static final BlockFamily UNDERSHALE = familyBuilder(AetherIIBlocks.UNDERSHALE.get())
            .wall(AetherIIBlocks.UNDERSHALE_WALL.get())
            .slab(AetherIIBlocks.UNDERSHALE_SLAB.get())
            .stairs(AetherIIBlocks.UNDERSHALE_STAIRS.get())
            .getFamily();
    public static final BlockFamily UNDERSHALE_BRICKS = familyBuilder(AetherIIBlocks.UNDERSHALE_BRICKS.get())
            .wall(AetherIIBlocks.UNDERSHALE_BRICK_WALL.get())
            .slab(AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get())
            .stairs(AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get())
            .getFamily();
    public static final BlockFamily ICHORITE = familyBuilder(AetherIIBlocks.ICHORITE.get())
            .wall(AetherIIBlocks.ICHORITE_WALL.get())
            .slab(AetherIIBlocks.ICHORITE_SLAB.get())
            .stairs(AetherIIBlocks.ICHORITE_STAIRS.get())
            .getFamily();
    public static final BlockFamily SMOOTH_ICHORITE = familyBuilder(AetherIIBlocks.SMOOTH_ICHORITE.get())
            .wall(AetherIIBlocks.SMOOTH_ICHORITE_WALL.get())
            .slab(AetherIIBlocks.SMOOTH_ICHORITE_SLAB.get())
            .stairs(AetherIIBlocks.SMOOTH_ICHORITE_STAIRS.get())
            .getFamily();
    public static final BlockFamily ICHORITE_BRICKS = familyBuilder(AetherIIBlocks.ICHORITE_BRICKS.get())
            .wall(AetherIIBlocks.ICHORITE_BRICK_WALL.get())
            .slab(AetherIIBlocks.ICHORITE_BRICK_SLAB.get())
            .stairs(AetherIIBlocks.ICHORITE_BRICK_STAIRS.get())
            .getFamily();
    public static final BlockFamily MARBLED_ICHORITE = familyBuilder(AetherIIBlocks.MARBLED_ICHORITE.get())
            .wall(AetherIIBlocks.MARBLED_ICHORITE_WALL.get())
            .slab(AetherIIBlocks.MARBLED_ICHORITE_SLAB.get())
            .stairs(AetherIIBlocks.MARBLED_ICHORITE_STAIRS.get())
            .getFamily();
    public static final BlockFamily MARBLED_BRICKS = familyBuilder(AetherIIBlocks.MARBLED_BRICKS.get())
            .wall(AetherIIBlocks.MARBLED_BRICK_WALL.get())
            .slab(AetherIIBlocks.MARBLED_BRICK_SLAB.get())
            .stairs(AetherIIBlocks.MARBLED_BRICK_STAIRS.get())
            .getFamily();
    public static final BlockFamily AGIOSITE = familyBuilder(AetherIIBlocks.AGIOSITE.get())
            .wall(AetherIIBlocks.AGIOSITE_WALL.get())
            .slab(AetherIIBlocks.AGIOSITE_SLAB.get())
            .stairs(AetherIIBlocks.AGIOSITE_STAIRS.get())
            .getFamily();
    public static final BlockFamily AGIOSITE_BRICKS = familyBuilder(AetherIIBlocks.AGIOSITE_BRICKS.get())
            .wall(AetherIIBlocks.AGIOSITE_BRICK_WALL.get())
            .slab(AetherIIBlocks.AGIOSITE_BRICK_SLAB.get())
            .stairs(AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get())
            .getFamily();
    public static final BlockFamily ICESTONE = familyBuilder(AetherIIBlocks.ICESTONE.get())
            .wall(AetherIIBlocks.ICESTONE_WALL.get())
            .slab(AetherIIBlocks.ICESTONE_SLAB.get())
            .stairs(AetherIIBlocks.ICESTONE_STAIRS.get())
            .getFamily();
    public static final BlockFamily ICESTONE_BRICKS = familyBuilder(AetherIIBlocks.ICESTONE_BRICKS.get())
            .wall(AetherIIBlocks.ICESTONE_BRICK_WALL.get())
            .slab(AetherIIBlocks.ICESTONE_BRICK_SLAB.get())
            .stairs(AetherIIBlocks.ICESTONE_BRICK_STAIRS.get())
            .getFamily();

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockfamily = MAP.put(baseBlock, builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
