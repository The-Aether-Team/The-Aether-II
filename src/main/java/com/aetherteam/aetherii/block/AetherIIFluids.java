package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.fluid.AlkahestFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, AetherII.MODID);

    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_ALKAHEST = FLUIDS.register("flowing_alkahest", () -> new AlkahestFluid.Flowing(AetherIIFluids.ALKAHEST_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> ALKAHEST = FLUIDS.register("alkahest", () -> new AlkahestFluid.Source(AetherIIFluids.ALKAHEST_PROPERTIES));

    public static final BaseFlowingFluid.Properties ALKAHEST_PROPERTIES = new BaseFlowingFluid.Properties(AetherIIFluidTypes.ALKAHEST_TYPE, ALKAHEST, FLOWING_ALKAHEST).block(AetherIIBlocks.ALKAHEST);
}
