package com.aetherteam.aetherii;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.transfer.CombinedResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.BucketResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;

import java.util.List;

public class AetherIICapabilities {
    private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, ResourceHandler<ItemResource>> CHEST_COMBINER_HANDLER = new DoubleBlockCombiner.Combiner<>() {
        @Override
        public ResourceHandler<ItemResource> acceptDouble(ChestBlockEntity chest1, ChestBlockEntity chest2) {
            return new CombinedResourceHandler<>(VanillaContainerWrapper.of(chest1), VanillaContainerWrapper.of(chest2));
        }

        @Override
        public ResourceHandler<ItemResource> acceptSingle(ChestBlockEntity chest) {
            return VanillaContainerWrapper.of(chest);
        }

        @Override
        public ResourceHandler<ItemResource> acceptNone() {
            return null;
        }
    };

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlock(Capabilities.Item.BLOCK,
                (level, pos, state, blockEntity, side) ->
                        ((ChestBlock) state.getBlock()).combine(state, level, pos, true).apply(CHEST_COMBINER_HANDLER),
                AetherIIBlocks.SKYROOT_CHEST.get(),
                AetherIIBlocks.SENTRY_CRATE.get(),
                AetherIIBlocks.SAGE_CHEST.get());

        var sidedVanillaContainers = List.of(
                AetherIIBlockEntityTypes.HOLYSTONE_FURNACE.get(),
                AetherIIBlockEntityTypes.AMBER_HOURGLASS.get(),
                AetherIIBlockEntityTypes.ALTAR.get(),
                AetherIIBlockEntityTypes.ALKAHEST_PURIFIER.get());
        for (var type : sidedVanillaContainers) {
            event.registerBlockEntity(Capabilities.Item.BLOCK, type, WorldlyContainerWrapper::new);
        }

        var nonSidedVanillaContainers = List.of(
                AetherIIBlockEntityTypes.VASE.get(),
                AetherIIBlockEntityTypes.GUARDIAN_DONATION_BOX.get(),
                AetherIIBlockEntityTypes.ABANDONED_BAG.get(),
                AetherIIBlockEntityTypes.FUNGAL_CACHE.get(),
                AetherIIBlockEntityTypes.GUARDIAN_DONATION_BOX.get());
        for (var type : nonSidedVanillaContainers) {
            event.registerBlockEntity(Capabilities.Item.BLOCK, type, (container, side) -> VanillaContainerWrapper.of(container));
        }

        var buckets = List.of(
                AetherIIItems.SKYROOT_BUCKET.get(),
                AetherIIItems.SKYROOT_WATER_BUCKET.get(),
                AetherIIItems.ARKENIUM_CANISTER.get(),
                AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
        for (var type : buckets) {
            event.registerItem(Capabilities.Fluid.ITEM, (stack, access) -> new BucketResourceHandler(access), type);
        }

        if (NeoForgeMod.MILK.isBound()) {
            event.registerItem(Capabilities.Fluid.ITEM, (stack, access) -> new BucketResourceHandler(access), AetherIIItems.SKYROOT_MILK_BUCKET);
        }

        event.registerEntity(Capabilities.Item.ENTITY, AetherIIEntityTypes.MOA.get(), (entity, ctx) -> {
            if (entity instanceof Moa moa) {
                return VanillaContainerWrapper.of(moa.getInventory());
            }
            return null;
        });
    }
}
