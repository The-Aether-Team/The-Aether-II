package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.natural.AercloudBlock;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class AetherIIBlocks { //todo abstract methods for stuff like logs and leaves
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AetherII.MODID);

    // Terrain
    public static final DeferredBlock<Block> AETHER_GRASS_BLOCK = register("aether_grass_block", () -> new AetherGrassBlock(Block.Properties.of().mapColor(MapColor.WARPED_WART_BLOCK).randomTicks().strength(0.6F).sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> AETHER_DIRT = register("aether_dirt", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> QUICKSOIL = register("quicksoil", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5F).friction(0.99F).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> HOLYSTONE = register("holystone", () -> new Block(Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> UNDERSHALE = register("undershale", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));

    // Ores
    public static final DeferredBlock<Block> AMBROSIUM_ORE = register("ambrosium_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).requiresCorrectToolForDrops().lightLevel(AetherIIBlocks::lightLevel11)));
    public static final DeferredBlock<Block> ZANITE_ORE = register("zanite_ore", () -> new DropExperienceBlock(UniformInt.of(3, 5), Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> ARKENIUM_ORE = register("arkenium_ore", () -> new DropExperienceBlock(UniformInt.of(3, 5), Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).randomTicks().requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> GRAVITITE_ORE = register("gravitite_ore", () -> new DropExperienceBlock(UniformInt.of(3, 5), Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F).randomTicks().requiresCorrectToolForDrops()));

    // Aerclouds
    public static final DeferredBlock<Block> COLD_AERCLOUD = register("cold_aercloud", () -> new AercloudBlock(Block.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.FLUTE).strength(0.3F).sound(SoundType.WOOL).noOcclusion().dynamicShape().isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));

    // Logs //todo map colors
    public static final DeferredBlock<RotatedPillarBlock> SKYROOT_LOG = register("skyroot_log", () -> new RotatedPillarBlock(Block.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<RotatedPillarBlock> GREATROOT_LOG = register("greatroot_log", () -> new RotatedPillarBlock(Block.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<RotatedPillarBlock> WISPROOT_LOG = register("wisproot_log", () -> new RotatedPillarBlock(Block.Properties.ofFullCopy(Blocks.OAK_LOG)));

    // Leaves //todo map colors
    public static final DeferredBlock<Block> SKYROOT_LEAVES = register("skyroot_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> SKYPLANE_LEAVES = register("skyplane_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> SKYBIRCH_LEAVES = register("skybirch_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> SKYPINE_LEAVES = register("skypine_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> WISPROOT_LEAVES = register("wisproot_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> WISPTOP_LEAVES = register("wisptop_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> GREATROOT_LEAVES = register("greatroot_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> GREATOAK_LEAVES = register("greatoak_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> GREATBOA_LEAVES = register("greatboa_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));
    public static final DeferredBlock<Block> AMBEROOT_LEAVES = register("amberoot_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AetherIIBlocks::ocelotOrParrot).isRedstoneConductor(AetherIIBlocks::never).isSuffocating(AetherIIBlocks::never).isViewBlocking(AetherIIBlocks::never)));

    private static <T extends Block> DeferredBlock<T> baseRegister(String name, Supplier<? extends T> block, Function<DeferredBlock<T>, Supplier<? extends Item>> item) {
        DeferredBlock<T> register = BLOCKS.register(name, block);
        AetherIIItems.ITEMS.register(name, item.apply(register));
        return register;
    }

    private static <B extends Block> DeferredBlock<B> register(String name, Supplier<B> block) {
        return baseRegister(name, block, AetherIIBlocks::registerBlockItem);
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final DeferredBlock<T> deferredBlock) {
        return () -> {
            DeferredBlock<T> block = Objects.requireNonNull(deferredBlock);
            return new BlockItem(block.get(), new Item.Properties());
        };
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean always(BlockState state, BlockGetter getter, BlockPos pos) {
        return true;
    }

    private static <A> boolean never(BlockState state, BlockGetter getter, BlockPos pos, A block) {
        return false;
    }

    private static boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static int lightLevel11(BlockState state) {
        return 11;
    }
}
