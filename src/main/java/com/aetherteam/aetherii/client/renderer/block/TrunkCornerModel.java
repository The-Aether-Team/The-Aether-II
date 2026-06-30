package com.aetherteam.aetherii.client.renderer.block;

import com.aetherteam.aetherii.block.natural.TrunkBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrunkCornerModel extends BakedModelWrapper<BakedModel> {
    public static final ModelProperty<Map<String, WallSide>> CORNER_DATA = new ModelProperty<>();

    private final Map<String, BakedModel> cornerModels;
    private final Map<String, BakedModel> cornerTallModels;

    public TrunkCornerModel(BakedModel originalModel, Map<String, BakedModel> cornerModels, Map<String, BakedModel> cornerTallModels) {
        super(originalModel);
        this.cornerModels = cornerModels;
        this.cornerTallModels = cornerTallModels;
    }

    @Override
    public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        ModelData originalData = this.originalModel.getModelData(level, pos, state, modelData);
        if (state.getBlock() instanceof TrunkBlock) {
            Map<String, WallSide> corners = Map.copyOf(TrunkBlock.getCornerProperties(level, pos));
            if (!corners.isEmpty()) {
                return originalData.derive().with(CORNER_DATA, corners).build();
            }
        }
        return originalData;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>(this.originalModel.getQuads(state, side, rand, extraData, renderType));
        Map<String, WallSide> corners = extraData.get(CORNER_DATA);
        if (state == null || corners == null || corners.isEmpty()) {
            return quads;
        }

        for (Map.Entry<String, WallSide> entry : corners.entrySet()) {
            BakedModel model = switch (entry.getValue()) {
                case LOW -> this.cornerModels.get(entry.getKey());
                case TALL -> this.cornerTallModels.get(entry.getKey());
                case NONE -> null;
            };
            if (model != null && (renderType == null || model.getRenderTypes(state, rand, ModelData.EMPTY).contains(renderType))) {
                quads.addAll(model.getQuads(state, side, rand, ModelData.EMPTY, renderType));
            }
        }
        return quads;
    }
}
