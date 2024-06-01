package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AetherII.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HolystoneFurnaceBlockEntity>> HOLYSTONE_FURNACE = BLOCK_ENTITY_TYPES.register("holystone_furnace", () ->
            BlockEntityType.Builder.of(HolystoneFurnaceBlockEntity::new, AetherIIBlocks.HOLYSTONE_FURNACE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SkyrootChestBlockEntity>> SKYROOT_CHEST = BLOCK_ENTITY_TYPES.register("skyroot_chest", () ->
            BlockEntityType.Builder.of(SkyrootChestBlockEntity::new, AetherIIBlocks.SKYROOT_CHEST.get()).build(null));
}