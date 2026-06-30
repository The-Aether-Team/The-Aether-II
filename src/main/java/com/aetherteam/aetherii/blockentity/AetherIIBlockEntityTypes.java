package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AetherII.MODID);

    public static final RegistryObject<BlockEntityType<IcestoneBlockEntity>> ICESTONE = BLOCK_ENTITY_TYPES.register("icestone", () ->
            BlockEntityType.Builder.of(IcestoneBlockEntity::new, AetherIIBlocks.ICESTONE.get(), AetherIIBlocks.ICESTONE_STAIRS.get(), AetherIIBlocks.ICESTONE_SLAB.get(), AetherIIBlocks.ICESTONE_WALL.get()).build(null));

    public static final RegistryObject<BlockEntityType<HolystoneFurnaceBlockEntity>> HOLYSTONE_FURNACE = BLOCK_ENTITY_TYPES.register("holystone_furnace", () ->
            BlockEntityType.Builder.of(HolystoneFurnaceBlockEntity::new, AetherIIBlocks.HOLYSTONE_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<HolystoneSmokerBlockEntity>> HOLYSTONE_SMOKER = BLOCK_ENTITY_TYPES.register("holystone_smoker", () ->
            BlockEntityType.Builder.of(HolystoneSmokerBlockEntity::new, AetherIIBlocks.HOLYSTONE_SMOKER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SkyrootChestBlockEntity>> SKYROOT_CHEST = BLOCK_ENTITY_TYPES.register("skyroot_chest", () ->
            BlockEntityType.Builder.of(SkyrootChestBlockEntity::new, AetherIIBlocks.SKYROOT_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<AmberHourglassBlockEntity>> AMBER_HOURGLASS = BLOCK_ENTITY_TYPES.register("amber_hourglass", () ->
            BlockEntityType.Builder.of(AmberHourglassBlockEntity::new, AetherIIBlocks.AMBER_HOURGLASS.get()).build(null));

    public static final RegistryObject<BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITY_TYPES.register("altar", () ->
            BlockEntityType.Builder.of(AltarBlockEntity::new, AetherIIBlocks.ALTAR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ArkeniumForgeBlockEntity>> ARKENIUM_FORGE = BLOCK_ENTITY_TYPES.register("arkenium_forge", () ->
            BlockEntityType.Builder.of(ArkeniumForgeBlockEntity::new, AetherIIBlocks.ARKENIUM_FORGE.get()).build(null));

    public static final RegistryObject<BlockEntityType<AlkahestPurifierBlockEntity>> ALKAHEST_PURIFIER = BLOCK_ENTITY_TYPES.register("alkahest_purifier", () ->
            BlockEntityType.Builder.of(AlkahestPurifierBlockEntity::new, AetherIIBlocks.ALKAHEST_PURIFIER.get()).build(null));

    public static final RegistryObject<BlockEntityType<MusicBlockEntity>> MUSIC_BLOCK = BLOCK_ENTITY_TYPES.register("music_block", () ->
            BlockEntityType.Builder.of(MusicBlockEntity::new, AetherIIBlocks.MUSIC_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ShelfBlockEntity>> SHELF = BLOCK_ENTITY_TYPES.register("shelf", () ->
            BlockEntityType.Builder.of(ShelfBlockEntity::new,
                    AetherIIBlocks.SKYROOT_SHELF.get(),
                    AetherIIBlocks.GREATROOT_SHELF.get(),
                    AetherIIBlocks.WISPROOT_SHELF.get(),
                    AetherIIBlocks.AMBEROOT_SHELF.get()).build(null));

    public static final RegistryObject<BlockEntityType<AmbrosiumCampfireBlockEntity>> AMBROSIUM_CAMPFIRE = BLOCK_ENTITY_TYPES.register("ambrosium_campfire", () ->
            BlockEntityType.Builder.of(AmbrosiumCampfireBlockEntity::new, AetherIIBlocks.AMBROSIUM_CAMPFIRE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SkyrootBedBlockEntity>> SKYROOT_BED = BLOCK_ENTITY_TYPES.register("skyroot_bed", () ->
            BlockEntityType.Builder.of(SkyrootBedBlockEntity::new,
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
            ).build(null));

    public static final RegistryObject<BlockEntityType<AnimalStashBlockEntity>> ANIMAL_STASH = BLOCK_ENTITY_TYPES.register("animal_stash", () ->
            BlockEntityType.Builder.of(AnimalStashBlockEntity::new, AetherIIBlocks.ANIMAL_STASH.get()).build(null));

    public static final RegistryObject<BlockEntityType<MoaEggBlockEntity>> MOA_EGG = BLOCK_ENTITY_TYPES.register("moa_egg", () ->
            BlockEntityType.Builder.of(MoaEggBlockEntity::new, AetherIIBlocks.MOA_EGG.get()).build(null));

    public static final RegistryObject<BlockEntityType<OutpostCampfireBlockEntity>> OUTPOST_CAMPFIRE = BLOCK_ENTITY_TYPES.register("outpost_campfire", () ->
            BlockEntityType.Builder.of(OutpostCampfireBlockEntity::new, AetherIIBlocks.OUTPOST_CAMPFIRE.get()).build(null));

    public static final RegistryObject<BlockEntityType<MuralBlockEntity>> MURAL = BLOCK_ENTITY_TYPES.register("mural", () ->
            BlockEntityType.Builder.of(MuralBlockEntity::new, AetherIIBlocks.MURAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<VaseBlockEntity>> VASE = BLOCK_ENTITY_TYPES.register("vase", () ->
            BlockEntityType.Builder.of(VaseBlockEntity::new, AetherIIBlocks.HOLYSTONE_VASE.get(), AetherIIBlocks.VERADEXIAN_VASE.get(), AetherIIBlocks.BREXALLEN_VASE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SentryCrateBlockEntity>> SENTRY_CRATE = BLOCK_ENTITY_TYPES.register("sentry_crate", () ->
            BlockEntityType.Builder.of(SentryCrateBlockEntity::new, AetherIIBlocks.SENTRY_CRATE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SentrySpawnerBlockEntity>> SENTRY_SPAWNER = BLOCK_ENTITY_TYPES.register("wall_spawner", () ->
            BlockEntityType.Builder.of(SentrySpawnerBlockEntity::new, AetherIIBlocks.SENTRY_SPAWNER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SentryTrapBlockEntity>> SENTRY_TRAP = BLOCK_ENTITY_TYPES.register("sentry_trap", () ->
            BlockEntityType.Builder.of(SentryTrapBlockEntity::new, AetherIIBlocks.SENTRY_TRAP.get()).build(null));

    public static final RegistryObject<BlockEntityType<GuardianDonationBoxBlockEntity>> GUARDIAN_DONATION_BOX = BLOCK_ENTITY_TYPES.register("guardian_donation_box", () ->
            BlockEntityType.Builder.of(GuardianDonationBoxBlockEntity::new, AetherIIBlocks.GUARDIAN_DONATION_BOX.get()).build(null));

    public static final RegistryObject<BlockEntityType<AbandonedBagBlockEntity>> ABANDONED_BAG = BLOCK_ENTITY_TYPES.register("abandoned_bag", () ->
            BlockEntityType.Builder.of(AbandonedBagBlockEntity::new, AetherIIBlocks.ABANDONED_BAG.get()).build(null));

    public static final RegistryObject<BlockEntityType<FungalCacheBlockEntity>> FUNGAL_CACHE = BLOCK_ENTITY_TYPES.register("fungal_cache", () ->
            BlockEntityType.Builder.of(FungalCacheBlockEntity::new, AetherIIBlocks.FUNGAL_CACHE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SageChestBlockEntity>> SAGE_CHEST = BLOCK_ENTITY_TYPES.register("sage_chest", () ->
            BlockEntityType.Builder.of(SageChestBlockEntity::new, AetherIIBlocks.SAGE_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<LockedBlockEntity>> LOCKED_BLOCK = BLOCK_ENTITY_TYPES.register("locked_block", () ->
            BlockEntityType.Builder.of(LockedBlockEntity::new, AetherIIBlocks.LOCKED_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<BossDoorwayBlockEntity>> BOSS_DOORWAY_BLOCK = BLOCK_ENTITY_TYPES.register("boss_doorway_block", () ->
            BlockEntityType.Builder.of(BossDoorwayBlockEntity::new, AetherIIBlocks.BOSS_DOORWAY_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<TreasureDoorwayBlockEntity>> TREASURE_DOORWAY_BLOCK = BLOCK_ENTITY_TYPES.register("treasure_doorway_block", () ->
            BlockEntityType.Builder.of(TreasureDoorwayBlockEntity::new, AetherIIBlocks.TREASURE_DOORWAY_BLOCK.get()).build(null));

    public static void registerValidBlockEntityTypes() {
        BlockEntityType.SIGN.validBlocks = ImmutableSet.<net.minecraft.world.level.block.Block>builder()
                .addAll(BlockEntityType.SIGN.validBlocks)
                .add(AetherIIBlocks.SKYROOT_SIGN.get())
                .add(AetherIIBlocks.SKYROOT_WALL_SIGN.get())
                .add(AetherIIBlocks.GREATROOT_SIGN.get())
                .add(AetherIIBlocks.GREATROOT_WALL_SIGN.get())
                .add(AetherIIBlocks.WISPROOT_SIGN.get())
                .add(AetherIIBlocks.WISPROOT_WALL_SIGN.get())
                .add(AetherIIBlocks.AMBEROOT_SIGN.get())
                .add(AetherIIBlocks.AMBEROOT_WALL_SIGN.get())
                .build();

        BlockEntityType.HANGING_SIGN.validBlocks = ImmutableSet.<net.minecraft.world.level.block.Block>builder()
                .addAll(BlockEntityType.HANGING_SIGN.validBlocks)
                .add(AetherIIBlocks.SKYROOT_HANGING_SIGN.get())
                .add(AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get())
                .add(AetherIIBlocks.GREATROOT_HANGING_SIGN.get())
                .add(AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get())
                .add(AetherIIBlocks.WISPROOT_HANGING_SIGN.get())
                .add(AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get())
                .add(AetherIIBlocks.AMBEROOT_HANGING_SIGN.get())
                .add(AetherIIBlocks.AMBEROOT_WALL_HANGING_SIGN.get())
                .build();
    }
}
