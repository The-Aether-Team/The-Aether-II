package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.MuralBlockEntity;
import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.google.common.collect.ImmutableList;
import com.mojang.math.Quadrant;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MuralModel extends DelegateBlockStateModel {
    private static final Direction[] DIRECTIONS = Arrays.copyOfRange(Direction.values(), 0, 7);
    private static final Map<MuralBlockEntity.MuralData, List<BlockModelPart>> CACHED_PARTS = new ConcurrentHashMap<>();

    public MuralModel(BlockStateModel delegate) {
        super(delegate);
	}

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        MuralBlockEntity.MuralData data = level.getModelData(pos).get(MuralBlockEntity.MuralData.PROPERTY);
        if (data == null) {
            super.collectParts(level, pos, state, random, parts);
            return;
        }

        List<BlockModelPart> modelParts = CACHED_PARTS.get(data);
        if (modelParts != null) {
            parts.addAll(modelParts);
            return;
        }

        List<BlockModelPart> blockModelParts = this.delegate.collectParts(level, pos, state, random);
        ImmutableList<BlockModelPart> rebakedModelParts = this.rebakeModelParts(blockModelParts, state, data);
        CACHED_PARTS.put(data, rebakedModelParts);
        parts.addAll(rebakedModelParts);
    }

    @NotNull
    private ImmutableList<BlockModelPart> rebakeModelParts(List<BlockModelPart> blockModelParts, BlockState state, MuralBlockEntity.MuralData data) {
        ImmutableList.Builder<BlockModelPart> partBuilder = ImmutableList.builder();
        for (BlockModelPart part : blockModelParts) {
            QuadCollection.Builder builder = new QuadCollection.Builder();
            for (Direction side : DIRECTIONS) {
                for (BakedQuad originalQuad : part.getQuads(side)) {
                    BakedQuad newModelQuad = side != data.facing() ? originalQuad : this.rebakeQuad(data, originalQuad);
                    if (side == null) {
                        builder.addUnculledFace(newModelQuad);
                    } else {
                        builder.addCulledFace(side, newModelQuad);
                    }
                }
            }
            partBuilder.add(new SimpleModelWrapper(builder.build(), part.useAmbientOcclusion(), part.particleIcon(), part.getRenderType(state)));
        }
		return partBuilder.build();
    }

    @NotNull
    private BakedQuad rebakeQuad(MuralBlockEntity.MuralData data, BakedQuad quad) {
        int[] bakedBuffer = quad.vertices();
        int[] rebakedSection = Arrays.copyOf(bakedBuffer, bakedBuffer.length); // Avoids mutating the original model in-memory

        TextureAtlasSprite muralSprite = AetherIIAtlases.MURAL_MATERIALS.get(new MuralSection(data.mural(), data.offsetX(), data.offsetY())).sprite();
        BlockElementFace.UVs uvs = shrinkUVs(muralSprite, new BlockElementFace.UVs(0, 0, 16, 16));

        for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
            int bufferOffset = vertexIndex * 8;

            float u = BlockElementFace.getU(uvs, Quadrant.R0, vertexIndex);
            float v = BlockElementFace.getV(uvs, Quadrant.R0, vertexIndex);

            rebakedSection[bufferOffset + 4] = Float.floatToRawIntBits(muralSprite.getU(u));
            rebakedSection[bufferOffset + 5] = Float.floatToRawIntBits(muralSprite.getV(v));
        }
        return new BakedQuad(rebakedSection, quad.tintIndex(), quad.direction(), muralSprite, quad.shade(), quad.lightEmission(), quad.hasAmbientOcclusion());
    }

    private static BlockElementFace.UVs shrinkUVs(TextureAtlasSprite sprite, BlockElementFace.UVs uvs) {
        float f = uvs.minU();
        float f1 = uvs.minV();
        float f2 = uvs.maxU();
        float f3 = uvs.maxV();
        float f4 = sprite.uvShrinkRatio();
        float f5 = (f + f + f2 + f2) / 4.0F;
        float f6 = (f1 + f1 + f3 + f3) / 4.0F;
        return new BlockElementFace.UVs(Mth.lerp(f4, f, f5), Mth.lerp(f4, f1, f6), Mth.lerp(f4, f2, f5), Mth.lerp(f4, f3, f6));
    }

    public static void registerReloadListener(AddClientReloadListenersEvent event) { // Clear cache as UVs can change from resource packs
        event.addListener(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "mural_cache"), (ResourceManagerReloadListener) resourceManager -> CACHED_PARTS.clear());
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) { // Clear stale holders to prevent memory leaks
        CACHED_PARTS.clear();
    }
}
