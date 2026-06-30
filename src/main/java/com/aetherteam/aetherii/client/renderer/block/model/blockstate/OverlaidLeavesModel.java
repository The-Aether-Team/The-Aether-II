package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class OverlaidLeavesModel extends BreakingFixModel {
    private static final ModelProperty<Map<Direction, Boolean>> CONNECTED_FACES = new ModelProperty<>();

    public OverlaidLeavesModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        ModelData originalData = this.originalModel.getModelData(level, pos, state, modelData);
        if (!(state.getBlock() instanceof AetherLeavesBlock)) {
            return originalData;
        }

        Map<Direction, Boolean> connectedFaces = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState relativeState = level.getBlockState(pos.relative(direction));
            boolean connected = relativeState.getBlock() instanceof AetherLeavesBlock
                    && relativeState.getValue(AetherLeavesBlock.SNOWY) == state.getValue(AetherLeavesBlock.SNOWY)
                    && relativeState.getValue(AetherLeavesBlock.MOSSY) == state.getValue(AetherLeavesBlock.MOSSY);
            connectedFaces.put(direction, connected);
        }
        return originalData.derive().with(CONNECTED_FACES, connectedFaces).build();
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource random, @NotNull ModelData modelData, @Nullable RenderType renderType) {
        List<BakedQuad> quads = this.originalModel.getQuads(state, side, random, modelData, renderType);
        if (state == null || side == null || quads.isEmpty()) {
            return quads;
        }

        if (side.getAxis().isHorizontal() && quads.size() >= 3) {
            Map<Direction, Boolean> connectedFaces = modelData.get(CONNECTED_FACES);
            boolean connected = connectedFaces != null && Boolean.TRUE.equals(connectedFaces.get(side));
            return connected ? List.of(quads.get(1)) : List.of(quads.get(0), quads.get(2));
        }

        if (side == Direction.UP && quads.size() >= 2) {
            return List.of(quads.get(0), quads.get(1));
        }

        return quads;
    }

    @Override
    public @NotNull List<BakedQuad> getBreakingQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource random, @NotNull ModelData modelData, @Nullable RenderType renderType) {
        List<BakedQuad> quads = this.getQuads(state, side, random, modelData, renderType);
        if (quads.size() <= 1) {
            return quads;
        }
        List<BakedQuad> breakingQuads = new ArrayList<>(1);
        breakingQuads.add(quads.get(quads.size() - 1));
        return breakingQuads;
    }
}
