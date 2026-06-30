package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;

public class AmbientOcclusionLightModel extends BakedModelWrapper<BakedModel> {
    public AmbientOcclusionLightModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean useAmbientOcclusion(BlockState state) {
        return true;
    }

    @Override
    public boolean useAmbientOcclusion(BlockState state, RenderType renderType) {
        return true;
    }
}
