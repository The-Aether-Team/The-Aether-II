package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class AetherIIBlockBuilders {
    public static Supplier<Block.Properties> aercloudProperties(MapColor mapColor) {
        return () -> Block.Properties.of()
                .mapColor(mapColor)
                .instrument(NoteBlockInstrument.FLUTE)
                .strength(0.3F)
                .sound(SoundType.WOOL)
                .noOcclusion()
                .dynamicShape()
                .forceSolidOn()
                .isValidSpawn((state, level, pos, entityType) -> entityType.is(AetherIITags.Entities.SPAWNING_AERCLOUDS))
                .isRedstoneConductor(AetherIIBlockBuilders::never)
                .isSuffocating(AetherIIBlockBuilders::never)
                .isViewBlocking(AetherIIBlockBuilders::never);
    }

    public static Supplier<Block.Properties> logProperties(MapColor topMapColor, MapColor sideMapColor) {
        return () -> Block.Properties.of()
                .mapColor(block -> block.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava();
    }

    @SuppressWarnings("deprecation")
    public static Supplier<Block.Properties> leafPileProperties(MapColor mapColor) {
        return () -> Block.Properties.of()
                .mapColor(mapColor)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .forceSolidOff()
                .isSuffocating(AetherIIBlockBuilders::never)
                .isViewBlocking(AetherIIBlockBuilders::never)
                .isRedstoneConductor(AetherIIBlockBuilders::never)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    public static Supplier<Block.Properties> leavesProperties(MapColor mapColor) {
        return () -> Block.Properties.of()
                .mapColor(mapColor)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn(AetherIIBlockBuilders::spawnOnLeaves)
                .isSuffocating(AetherIIBlockBuilders::never)
                .isViewBlocking(AetherIIBlockBuilders::never)
                .isRedstoneConductor(AetherIIBlockBuilders::never)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    public static Supplier<Block.Properties> arilumLanternProperties(MapColor mapColor) {
        return () -> Block.Properties.of()
                .mapColor(mapColor)
                .friction(0.8F)
                .noOcclusion()
                .sound(SoundType.FROGLIGHT)
                .lightLevel((state) -> 8);
    }

    public static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    public static boolean always(BlockState state, BlockGetter getter, BlockPos pos) {
        return true;
    }

    public static <A> boolean never(BlockState state, BlockGetter getter, BlockPos pos, A block) {
        return false;
    }

    public static boolean spawnOnLeaves(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return entityType.is(AetherIITags.Entities.SPAWNING_LEAVES);
    }

    public static int lightLevel8(BlockState state) {
        return 8;
    }

    public static int lightLevel11(BlockState state) {
        return 11;
    }
}