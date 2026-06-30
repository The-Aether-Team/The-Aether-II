package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BreakingFixModel extends BakedModelWrapper<BakedModel> {
    public BreakingFixModel(BakedModel originalModel) {
        super(originalModel);
    }

    public @NotNull List<BakedQuad> getBreakingQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource random, @NotNull ModelData modelData, @Nullable RenderType renderType) {
        List<BakedQuad> quads = this.originalModel.getQuads(state, side, random, modelData, renderType);
        return quads.isEmpty() ? quads : List.of(quads.get(quads.size() - 1));
    }
}
