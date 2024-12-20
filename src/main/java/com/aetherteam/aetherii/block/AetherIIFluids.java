package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.fluid.AcidFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, AetherII.MODID);

    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_ACID = FLUIDS.register("flowing_acid", () -> new AcidFluid.Flowing(AetherIIFluids.ACID_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> ACID = FLUIDS.register("acid", () -> new AcidFluid.Source(AetherIIFluids.ACID_PROPERTIES));

    public static final BaseFlowingFluid.Properties ACID_PROPERTIES = new BaseFlowingFluid.Properties(AetherIIFluidTypes.ACID_TYPE, ACID, FLOWING_ACID).block(AetherIIBlocks.ACID); //.bucket(UGItems.VIRULENT_MIX_BUCKET)
}
