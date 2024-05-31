package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import com.aetherteam.aetherii.block.natural.AetherLeavesPileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class AetherIIBlockBuilders {
    public static RotatedPillarBlock log(MapColor topMapColor, MapColor sideMapColor) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(block -> block.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F)
                        .sound(SoundType.WOOD)
                        .ignitedByLava()
        );
    }

    public static Block leavesPile(MapColor mapColor) {
        return new AetherLeavesPileBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .strength(0.2F)
                        .randomTicks()
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .replaceable()
                        .forceSolidOff()
                        .isValidSpawn(AetherIIBlockBuilders::ocelotOrParrot)
                        .isSuffocating(AetherIIBlockBuilders::never)
                        .isViewBlocking(AetherIIBlockBuilders::never)
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
                        .isRedstoneConductor(AetherIIBlockBuilders::never)
        );
    }

    public static Block leaves(MapColor mapColor, Supplier<SimpleParticleType> leavesParticle, Supplier<Block> leavesPile) {
        return new AetherLeavesBlock(
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
                        .isRedstoneConductor(AetherIIBlockBuilders::never),
                leavesParticle, leavesPile
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