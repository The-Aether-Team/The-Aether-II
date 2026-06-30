package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.blockentity.MuralBlockEntity;
import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.client.AetherIIClientCaches;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MuralModel extends BakedModelWrapper<BakedModel> {
    public MuralModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource random, @NotNull ModelData modelData, @Nullable RenderType renderType) {
        MuralBlockEntity.MuralData data = modelData.get(MuralBlockEntity.MuralData.PROPERTY);
        if (data == null || side != data.facing()) {
            return this.originalModel.getQuads(state, side, random, modelData, renderType);
        }

        List<BakedQuad> cachedQuads = AetherIIClientCaches.CACHED_MURAL_BLOCK_PARTS.get(data);
        if (cachedQuads != null) {
            return cachedQuads;
        }

        List<BakedQuad> originalQuads = this.originalModel.getQuads(state, side, random, modelData, renderType);
        List<BakedQuad> rebakedQuads = new ArrayList<>(originalQuads.size());
        for (BakedQuad originalQuad : originalQuads) {
            rebakedQuads.add(rebakeQuad(data.section(), originalQuad));
        }
        List<BakedQuad> immutableQuads = List.copyOf(rebakedQuads);
        AetherIIClientCaches.CACHED_MURAL_BLOCK_PARTS.put(data, immutableQuads);
        return immutableQuads;
    }

    @NotNull
    public static BakedQuad rebakeQuad(MuralSection section, BakedQuad quad) {
        int[] rebakedVertices = Arrays.copyOf(quad.getVertices(), quad.getVertices().length);
        TextureAtlasSprite muralSprite = getMuralSprite(section);
        BlockFaceUV uvs = shrinkUVs(muralSprite, new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0));

        for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
            int bufferOffset = vertexIndex * 8;
            rebakedVertices[bufferOffset + 4] = Float.floatToRawIntBits(muralSprite.getU(uvs.getU(vertexIndex)));
            rebakedVertices[bufferOffset + 5] = Float.floatToRawIntBits(muralSprite.getV(uvs.getV(vertexIndex)));
        }
        return new BakedQuad(rebakedVertices, quad.getTintIndex(), quad.getDirection(), muralSprite, quad.isShade());
    }

    private static TextureAtlasSprite getMuralSprite(MuralSection section) {
        ResourceLocation assetId = section.mural().value().assetId();
        ResourceLocation texture = new ResourceLocation(assetId.getNamespace(), "entity/mural/" + assetId.getPath() + "_" + section.offsetX() + "_" + section.offsetY());
        return net.minecraft.client.Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(texture);
    }

    private static BlockFaceUV shrinkUVs(TextureAtlasSprite sprite, BlockFaceUV uvs) {
        float minU = uvs.uvs[0];
        float minV = uvs.uvs[1];
        float maxU = uvs.uvs[2];
        float maxV = uvs.uvs[3];
        float shrink = sprite.uvShrinkRatio();
        float centerU = (minU + minU + maxU + maxU) / 4.0F;
        float centerV = (minV + minV + maxV + maxV) / 4.0F;
        return new BlockFaceUV(new float[]{
                Mth.lerp(shrink, minU, centerU),
                Mth.lerp(shrink, minV, centerV),
                Mth.lerp(shrink, maxU, centerU),
                Mth.lerp(shrink, maxV, centerV)
        }, uvs.rotation);
    }
}
