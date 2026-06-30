package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CopyBlockModel extends BakedModelWrapper<BakedModel> {
    private static final int COPY_TINT = -4276546;

    public CopyBlockModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource random, @NotNull ModelData modelData, @Nullable RenderType renderType) {
        CopyBlockEntity.CopyData data = modelData.get(CopyBlockEntity.CopyData.PROPERTY);
        if (data == null) {
            return Collections.emptyList();
        }
        return this.getCopyQuads(data.state(), side, random, renderType);
    }

    public @NotNull List<BakedQuad> getCopyQuads(BlockState state, @Nullable Direction side, @NotNull RandomSource random, @Nullable RenderType renderType) {
        BakedModel sourceModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
        return copyQuads(sourceModel.getQuads(state, side, random, ModelData.EMPTY, renderType));
    }

    public static @NotNull List<BakedQuad> copyQuads(List<BakedQuad> quads) {
        if (quads.isEmpty()) {
            return quads;
        }
        List<BakedQuad> copy = new ArrayList<>(quads.size());
        for (BakedQuad quad : quads) {
            copy.add(copyQuad(quad));
        }
        return copy;
    }

    private static BakedQuad copyQuad(BakedQuad quad) {
        int[] vertices = quad.getVertices().clone();
        for (int i = 0; i < 4; i++) {
            vertices[i * 8 + 3] = COPY_TINT;
        }
        return new BakedQuad(vertices, -1, quad.getDirection(), quad.getSprite(), quad.isShade());
    }

    @Override
    public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        ModelData originalData = this.originalModel.getModelData(level, pos, state, modelData);
        if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
            return originalData.derive().with(CopyBlockEntity.CopyData.PROPERTY, new CopyBlockEntity.CopyData(blockEntity.getCopyState())).build();
        }
        return originalData;
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull ModelData modelData) {
        CopyBlockEntity.CopyData data = modelData.get(CopyBlockEntity.CopyData.PROPERTY);
        if (data != null) {
            BakedModel sourceModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(data.state());
            return sourceModel.getParticleIcon(ModelData.EMPTY);
        }
        return super.getParticleIcon(modelData);
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource random, @NotNull ModelData modelData) {
        CopyBlockEntity.CopyData data = modelData.get(CopyBlockEntity.CopyData.PROPERTY);
        if (data != null) {
            return Minecraft.getInstance().getBlockRenderer().getBlockModel(data.state()).getRenderTypes(data.state(), random, ModelData.EMPTY);
        }
        return super.getRenderTypes(state, random, modelData);
    }
}
