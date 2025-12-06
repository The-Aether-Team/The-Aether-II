package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AetherII.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IcestoneBlockEntity>> ICESTONE = BLOCK_ENTITY_TYPES.register("icestone", () ->
            new BlockEntityType<>(IcestoneBlockEntity::new, AetherIIBlocks.ICESTONE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HolystoneFurnaceBlockEntity>> HOLYSTONE_FURNACE = BLOCK_ENTITY_TYPES.register("holystone_furnace", () ->
            new BlockEntityType<>(HolystoneFurnaceBlockEntity::new, AetherIIBlocks.HOLYSTONE_FURNACE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootChestBlockEntity>> SKYROOT_CHEST = BLOCK_ENTITY_TYPES.register("skyroot_chest", () ->
            new BlockEntityType<>(SkyrootChestBlockEntity::new, AetherIIBlocks.SKYROOT_CHEST.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MoaEggBlockEntity>> MOA_EGG = BLOCK_ENTITY_TYPES.register("moa_egg", () ->
            new BlockEntityType<>(MoaEggBlockEntity::new, AetherIIBlocks.MOA_EGG.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITY_TYPES.register("altar", () ->
            new BlockEntityType<>(AltarBlockEntity::new, AetherIIBlocks.ALTAR.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArkeniumForgeBlockEntity>> ARKENIUM_FORGE = BLOCK_ENTITY_TYPES.register("arkenium_forge", () ->
            new BlockEntityType<>(ArkeniumForgeBlockEntity::new, AetherIIBlocks.ARKENIUM_FORGE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlkahestPurifierBlockEntity>> ALKAHEST_PURIFIER = BLOCK_ENTITY_TYPES.register("alkahest_purifier", () ->
            new BlockEntityType<>(AlkahestPurifierBlockEntity::new, AetherIIBlocks.ALKAHEST_PURIFIER.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AmbrosiumCampfireBlockEntity>> AMBROSIUM_CAMPFIRE = BLOCK_ENTITY_TYPES.register("ambrosium_campfire", () ->
            new BlockEntityType<>(AmbrosiumCampfireBlockEntity::new, AetherIIBlocks.AMBROSIUM_CAMPFIRE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootBedBlockEntity>> SKYROOT_BED = BLOCK_ENTITY_TYPES.register("skyroot_bed", () ->
            new BlockEntityType<>(SkyrootBedBlockEntity::new, AetherIIBlocks.SKYROOT_BED.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OutpostCampfireBlockEntity>> OUTPOST_CAMPFIRE = BLOCK_ENTITY_TYPES.register("outpost_campfire", () ->
            new BlockEntityType<>(OutpostCampfireBlockEntity::new, AetherIIBlocks.OUTPOST_CAMPFIRE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MuralBlockEntity>> MURAL = BLOCK_ENTITY_TYPES.register("mural", () ->
            new BlockEntityType<>(MuralBlockEntity::new, AetherIIBlocks.MURAL.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SentryCrateBlockEntity>> SENTRY_CRATE = BLOCK_ENTITY_TYPES.register("sentry_crate", () ->
            new BlockEntityType<>(SentryCrateBlockEntity::new, AetherIIBlocks.SENTRY_CRATE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WallSpawnerBlockEntity>> WALL_SPAWNER = BLOCK_ENTITY_TYPES.register("wall_spawner", () ->
            new BlockEntityType<>(WallSpawnerBlockEntity::new, AetherIIBlocks.SENTRY_SPAWNER.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GroundTrapBlockEntity>> GROUND_TRAP = BLOCK_ENTITY_TYPES.register("ground_trap", () ->
            new BlockEntityType<>(GroundTrapBlockEntity::new, AetherIIBlocks.SENTRY_TRAP.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LockedBlockEntity>> LOCKED_BLOCK = BLOCK_ENTITY_TYPES.register("locked_block", () ->
            new BlockEntityType<>(LockedBlockEntity::new, AetherIIBlocks.LOCKED_BLOCK.get()));

    public static void registerValidBlockEntityTypes(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, AetherIIBlocks.SKYROOT_WALL_SIGN.get(), AetherIIBlocks.SKYROOT_SIGN.get(),
                AetherIIBlocks.GREATROOT_WALL_SIGN.get(), AetherIIBlocks.GREATROOT_SIGN.get(),
                AetherIIBlocks.WISPROOT_WALL_SIGN.get(), AetherIIBlocks.WISPROOT_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.SKYROOT_HANGING_SIGN.get(),
                AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.GREATROOT_HANGING_SIGN.get(),
                AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.WISPROOT_HANGING_SIGN.get());
    }
}