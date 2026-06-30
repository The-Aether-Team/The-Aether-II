package com.aetherteam.aetherii.client.renderer.block.model.builder;

import com.aetherteam.aetherii.client.renderer.block.model.blockstate.TrunkModel;
import net.minecraft.resources.ResourceLocation;

public class TrunkModelBuilder {
    private final ResourceLocation corner;
    private final ResourceLocation cornerTall;

    public TrunkModelBuilder(ResourceLocation corner, ResourceLocation cornerTall) {
        this.corner = corner;
        this.cornerTall = cornerTall;
    }

    public TrunkModel.Unbaked toUnbaked() {
        return new TrunkModel.Unbaked(this.corner, this.cornerTall);
    }
}
