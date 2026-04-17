package com.aetherteam.aetherii.client.renderer.block.model.builder;

import com.aetherteam.aetherii.client.renderer.block.model.blockstate.TrunkModel;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.client.model.generators.blockstate.CustomBlockStateModelBuilder;
import net.neoforged.neoforge.client.model.generators.blockstate.UnbakedMutator;

public class TrunkModelBuilder extends CustomBlockStateModelBuilder {
    private final Identifier corner;
    private final Identifier cornerTall;

    public TrunkModelBuilder(Identifier corner, Identifier cornerTall) {
        this.corner = corner;
        this.cornerTall = cornerTall;
    }

    @Override
    public CustomBlockStateModelBuilder with(VariantMutator variantMutator) {
        return this;
    }

    @Override
    public CustomBlockStateModelBuilder with(UnbakedMutator unbakedMutator) {
        return this;
    }

    @Override
    public CustomUnbakedBlockStateModel toUnbaked() {
        return new TrunkModel.Unbaked(this.corner, this.cornerTall);
    }
}