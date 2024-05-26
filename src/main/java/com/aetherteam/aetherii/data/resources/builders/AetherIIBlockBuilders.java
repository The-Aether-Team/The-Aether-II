package com.aetherteam.aetherii.data.resources.builders;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class AetherIIBlockBuilders {

    public static Block leaves(MapColor mapColor) {
        return new LeavesBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .strength(0.2F)
                        .randomTicks()
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .isValidSpawn(AetherIIBlockBuilders::ocelotOrParrot)
                        .isSuffocating(AetherIIBlockBuilders::never)
                        .isViewBlocking(AetherIIBlockBuilders::never)
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
                        .isRedstoneConductor(AetherIIBlockBuilders::never)
        );
    }

    public static BlockBehaviour.Properties aercloud(MapColor mapColor) {
        return BlockBehaviour.Properties.of()
                .mapColor(mapColor)
                .instrument(NoteBlockInstrument.FLUTE)
                .strength(0.3F)
                .sound(SoundType.WOOL)
                .noOcclusion()
                .dynamicShape()
                .isRedstoneConductor(AetherIIBlockBuilders::never)
                .isSuffocating(AetherIIBlockBuilders::never)
                .isViewBlocking(AetherIIBlockBuilders::never);
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

    public static boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    public static int lightLevel11(BlockState state) {
        return 11;
    }
}