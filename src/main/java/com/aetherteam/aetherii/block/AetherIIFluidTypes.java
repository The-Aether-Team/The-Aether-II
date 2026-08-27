package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.fluidtype.AlkahestFluidType;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AetherIIFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, AetherII.MODID);

    public static final DeferredHolder<FluidType, FluidType> ALKAHEST_TYPE = FLUID_TYPES.register("alkahest", () -> new AlkahestFluidType(FluidType.Properties.create()
                    .descriptionId("block.aether_ii.alkahest")
                    .canExtinguish(false)
                    .supportsBoating(false)
                    .pathType(PathType.DAMAGE_CAUTIOUS)
                    .adjacentPathType(null)
                    .sound(SoundActions.BUCKET_FILL, AetherIISoundEvents.ITEM_ARKENIUM_CANISTER_FILL_ALKAHEST.get())
                    .sound(SoundActions.BUCKET_EMPTY, AetherIISoundEvents.ITEM_ARKENIUM_CANISTER_EMPTY_ALKAHEST.get())
                    .lightLevel(8)
                    .addDripstoneDripping(0.0F, AetherIIParticleTypes.DRIPPING_DRIPSTONE_ALKAHEST.get(), Blocks.CAULDRON, SoundEvents.EMPTY)
            )
    );
}
