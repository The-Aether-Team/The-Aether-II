package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AetherIIFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, AetherII.MODID);

    public static final DeferredHolder<FluidType, FluidType> ACID_TYPE = FLUID_TYPES.register("acid", () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("block.aether_ii.acid")
                    .canExtinguish(false)
                    .supportsBoating(false)
                    .pathType(PathType.DAMAGE_CAUTIOUS)
                    .adjacentPathType(null)
//                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
//                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .lightLevel(8)
                    .addDripstoneDripping(0.0F, AetherIIParticleTypes.DRIPPING_DRIPSTONE_ACID.get(), Blocks.CAULDRON, SoundEvents.EMPTY)
            )
    );
}
