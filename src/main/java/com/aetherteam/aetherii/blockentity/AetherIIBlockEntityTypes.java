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
            new BlockEntityType<>(IcestoneBlockEntity::new, AetherIIBlocks.ICESTONE.get(), AetherIIBlocks.ICESTONE_STAIRS.get(), AetherIIBlocks.ICESTONE_SLAB.get(), AetherIIBlocks.ICESTONE_WALL.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HolystoneFurnaceBlockEntity>> HOLYSTONE_FURNACE = BLOCK_ENTITY_TYPES.register("holystone_furnace", () ->
            new BlockEntityType<>(HolystoneFurnaceBlockEntity::new, AetherIIBlocks.HOLYSTONE_FURNACE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HolystoneSmokerBlockEntity>> HOLYSTONE_SMOKER = BLOCK_ENTITY_TYPES.register("holystone_smoker", () ->
            new BlockEntityType<>(HolystoneSmokerBlockEntity::new, AetherIIBlocks.HOLYSTONE_SMOKER.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootChestBlockEntity>> SKYROOT_CHEST = BLOCK_ENTITY_TYPES.register("skyroot_chest", () ->
            new BlockEntityType<>(SkyrootChestBlockEntity::new, AetherIIBlocks.SKYROOT_CHEST.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AmberHourglassBlockEntity>> AMBER_HOURGLASS = BLOCK_ENTITY_TYPES.register("amber_hourglass", () ->
            new BlockEntityType<>(AmberHourglassBlockEntity::new, AetherIIBlocks.AMBER_HOURGLASS.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITY_TYPES.register("altar", () ->
            new BlockEntityType<>(AltarBlockEntity::new, AetherIIBlocks.ALTAR.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArkeniumForgeBlockEntity>> ARKENIUM_FORGE = BLOCK_ENTITY_TYPES.register("arkenium_forge", () ->
            new BlockEntityType<>(ArkeniumForgeBlockEntity::new, AetherIIBlocks.ARKENIUM_FORGE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlkahestPurifierBlockEntity>> ALKAHEST_PURIFIER = BLOCK_ENTITY_TYPES.register("alkahest_purifier", () ->
            new BlockEntityType<>(AlkahestPurifierBlockEntity::new, AetherIIBlocks.ALKAHEST_PURIFIER.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MusicBlockEntity>> MUSIC_BLOCK = BLOCK_ENTITY_TYPES.register("music_block", () ->
            new BlockEntityType<>(MusicBlockEntity::new, AetherIIBlocks.MUSIC_BLOCK.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AmbrosiumCampfireBlockEntity>> AMBROSIUM_CAMPFIRE = BLOCK_ENTITY_TYPES.register("ambrosium_campfire", () ->
            new BlockEntityType<>(AmbrosiumCampfireBlockEntity::new, AetherIIBlocks.AMBROSIUM_CAMPFIRE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootBedBlockEntity>> SKYROOT_BED = BLOCK_ENTITY_TYPES.register("skyroot_bed", () ->
            new BlockEntityType<>(SkyrootBedBlockEntity::new,
                    AetherIIBlocks.SKYROOT_BED.get(),
                    AetherIIBlocks.WHITE_SKYROOT_BED.get(),
                    AetherIIBlocks.ORANGE_SKYROOT_BED.get(),
                    AetherIIBlocks.MAGENTA_SKYROOT_BED.get(),
                    AetherIIBlocks.LIGHT_BLUE_SKYROOT_BED.get(),
                    AetherIIBlocks.YELLOW_SKYROOT_BED.get(),
                    AetherIIBlocks.LIME_SKYROOT_BED.get(),
                    AetherIIBlocks.PINK_SKYROOT_BED.get(),
                    AetherIIBlocks.GRAY_SKYROOT_BED.get(),
                    AetherIIBlocks.LIGHT_GRAY_SKYROOT_BED.get(),
                    AetherIIBlocks.CYAN_SKYROOT_BED.get(),
                    AetherIIBlocks.PURPLE_SKYROOT_BED.get(),
                    AetherIIBlocks.BLUE_SKYROOT_BED.get(),
                    AetherIIBlocks.BROWN_SKYROOT_BED.get(),
                    AetherIIBlocks.GREEN_SKYROOT_BED.get(),
                    AetherIIBlocks.RED_SKYROOT_BED.get(),
                    AetherIIBlocks.BLACK_SKYROOT_BED.get()
            ));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AnimalStashBlockEntity>> ANIMAL_STASH = BLOCK_ENTITY_TYPES.register("animal_stash", () ->
            new BlockEntityType<>(AnimalStashBlockEntity::new, AetherIIBlocks.ANIMAL_STASH.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MoaEggBlockEntity>> MOA_EGG = BLOCK_ENTITY_TYPES.register("moa_egg", () ->
            new BlockEntityType<>(MoaEggBlockEntity::new, AetherIIBlocks.MOA_EGG.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OutpostCampfireBlockEntity>> OUTPOST_CAMPFIRE = BLOCK_ENTITY_TYPES.register("outpost_campfire", () ->
            new BlockEntityType<>(OutpostCampfireBlockEntity::new, AetherIIBlocks.OUTPOST_CAMPFIRE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MuralBlockEntity>> MURAL = BLOCK_ENTITY_TYPES.register("mural", () ->
            new BlockEntityType<>(MuralBlockEntity::new, AetherIIBlocks.MURAL.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TheranGlobeBlockEntity>> THERAN_GLOBE = BLOCK_ENTITY_TYPES.register("theran_globe", () ->
            new BlockEntityType<>(TheranGlobeBlockEntity::new, AetherIIBlocks.THERAN_GLOBE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VaseBlockEntity>> VASE = BLOCK_ENTITY_TYPES.register("vase", () ->
            new BlockEntityType<>(VaseBlockEntity::new, AetherIIBlocks.HOLYSTONE_VASE.get(), AetherIIBlocks.VERADEXIAN_VASE.get(), AetherIIBlocks.BREXALLEN_VASE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SentryCrateBlockEntity>> SENTRY_CRATE = BLOCK_ENTITY_TYPES.register("sentry_crate", () ->
            new BlockEntityType<>(SentryCrateBlockEntity::new, AetherIIBlocks.SENTRY_CRATE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SentrySpawnerBlockEntity>> SENTRY_SPAWNER = BLOCK_ENTITY_TYPES.register("wall_spawner", () ->
            new BlockEntityType<>(SentrySpawnerBlockEntity::new, AetherIIBlocks.SENTRY_SPAWNER.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SentryTrapBlockEntity>> SENTRY_TRAP = BLOCK_ENTITY_TYPES.register("sentry_trap", () ->
            new BlockEntityType<>(SentryTrapBlockEntity::new, AetherIIBlocks.SENTRY_TRAP.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GuardianDonationBoxBlockEntity>> GUARDIAN_DONATION_BOX = BLOCK_ENTITY_TYPES.register("guardian_donation_box", () ->
            new BlockEntityType<>(GuardianDonationBoxBlockEntity::new, AetherIIBlocks.GUARDIAN_DONATION_BOX.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AbandonedBagBlockEntity>> ABANDONED_BAG = BLOCK_ENTITY_TYPES.register("abandoned_bag", () ->
            new BlockEntityType<>(AbandonedBagBlockEntity::new, AetherIIBlocks.ABANDONED_BAG.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FungalCacheBlockEntity>> FUNGAL_CACHE = BLOCK_ENTITY_TYPES.register("fungal_cache", () ->
            new BlockEntityType<>(FungalCacheBlockEntity::new, AetherIIBlocks.FUNGAL_CACHE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SageChestBlockEntity>> SAGE_CHEST = BLOCK_ENTITY_TYPES.register("sage_chest", () ->
            new BlockEntityType<>(SageChestBlockEntity::new, AetherIIBlocks.SAGE_CHEST.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LockedBlockEntity>> LOCKED_BLOCK = BLOCK_ENTITY_TYPES.register("locked_block", () ->
            new BlockEntityType<>(LockedBlockEntity::new, AetherIIBlocks.LOCKED_BLOCK.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BossDoorwayBlockEntity>> BOSS_DOORWAY_BLOCK = BLOCK_ENTITY_TYPES.register("boss_doorway_block", () ->
            new BlockEntityType<>(BossDoorwayBlockEntity::new, AetherIIBlocks.BOSS_DOORWAY_BLOCK.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TreasureDoorwayBlockEntity>> TREASURE_DOORWAY_BLOCK = BLOCK_ENTITY_TYPES.register("treasure_doorway_block", () ->
            new BlockEntityType<>(TreasureDoorwayBlockEntity::new, AetherIIBlocks.TREASURE_DOORWAY_BLOCK.get()));

    public static void registerValidBlockEntityTypes(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN,
                AetherIIBlocks.SKYROOT_WALL_SIGN.get(), AetherIIBlocks.SKYROOT_SIGN.get(),
                AetherIIBlocks.GREATROOT_WALL_SIGN.get(), AetherIIBlocks.GREATROOT_SIGN.get(),
                AetherIIBlocks.WISPROOT_WALL_SIGN.get(), AetherIIBlocks.WISPROOT_SIGN.get(),
                AetherIIBlocks.AMBEROOT_WALL_SIGN.get(), AetherIIBlocks.AMBEROOT_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN,
                AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.SKYROOT_HANGING_SIGN.get(),
                AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.GREATROOT_HANGING_SIGN.get(),
                AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.WISPROOT_HANGING_SIGN.get(),
                AetherIIBlocks.AMBEROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.AMBEROOT_HANGING_SIGN.get());
        event.modify(BlockEntityType.SHELF,
                AetherIIBlocks.SKYROOT_SHELF.get(),
                AetherIIBlocks.GREATROOT_SHELF.get(),
                AetherIIBlocks.WISPROOT_SHELF.get(),
                AetherIIBlocks.AMBEROOT_SHELF.get());
        event.modify(BlockEntityType.BARREL,
                AetherIIBlocks.SKYROOT_BARREL.get());
    }
}