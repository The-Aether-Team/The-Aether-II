package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AetherII.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IcestoneBlockEntity>> ICESTONE = BLOCK_ENTITY_TYPES.register("icestone", () ->
            BlockEntityType.Builder.of(IcestoneBlockEntity::new, AetherIIBlocks.ICESTONE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HolystoneFurnaceBlockEntity>> HOLYSTONE_FURNACE = BLOCK_ENTITY_TYPES.register("holystone_furnace", () ->
            BlockEntityType.Builder.of(HolystoneFurnaceBlockEntity::new, AetherIIBlocks.HOLYSTONE_FURNACE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootChestBlockEntity>> SKYROOT_CHEST = BLOCK_ENTITY_TYPES.register("skyroot_chest", () ->
            BlockEntityType.Builder.of(SkyrootChestBlockEntity::new, AetherIIBlocks.SKYROOT_CHEST.get()).build(null));
  
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MoaEggBlockEntity>> MOA_EGG = BLOCK_ENTITY_TYPES.register("moa_egg", () ->
            BlockEntityType.Builder.of(MoaEggBlockEntity::new, AetherIIBlocks.MOA_EGG.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITY_TYPES.register("altar", () ->
            BlockEntityType.Builder.of(AltarBlockEntity::new, AetherIIBlocks.ALTAR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootBedBlockEntity>> SKYROOT_BED = BLOCK_ENTITY_TYPES.register("skyroot_bed", () ->
            BlockEntityType.Builder.of(SkyrootBedBlockEntity::new, AetherIIBlocks.SKYROOT_BED.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AetherSignBlockEntity>> AETHER_SIGN = BLOCK_ENTITY_TYPES.register("aether_sign", () ->
            BlockEntityType.Builder.of(AetherSignBlockEntity::new, AetherIIBlocks.SKYROOT_WALL_SIGN.get(), AetherIIBlocks.SKYROOT_SIGN.get(),
                    AetherIIBlocks.GREATROOT_WALL_SIGN.get(), AetherIIBlocks.GREATROOT_SIGN.get(),
                    AetherIIBlocks.WISPROOT_WALL_SIGN.get(), AetherIIBlocks.WISPROOT_SIGN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AetherHangingSignBlockEntity>> AETHER_HANGING_SIGN = BLOCK_ENTITY_TYPES.register("aether_hanging_sign", () ->
            BlockEntityType.Builder.of(AetherHangingSignBlockEntity::new, AetherIIBlocks.SKYROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.SKYROOT_HANGING_SIGN.get(),
                    AetherIIBlocks.GREATROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.GREATROOT_HANGING_SIGN.get(),
                    AetherIIBlocks.WISPROOT_WALL_HANGING_SIGN.get(), AetherIIBlocks.WISPROOT_HANGING_SIGN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OutpostCampfireBlockEntity>> OUTPOST_CAMPFIRE = BLOCK_ENTITY_TYPES.register("outpost_campfire", () ->
            BlockEntityType.Builder.of(OutpostCampfireBlockEntity::new, AetherIIBlocks.OUTPOST_CAMPFIRE.get()).build(null));
}