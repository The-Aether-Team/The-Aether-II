package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.MuralBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.renderer.texture.TextureAtlas;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MuralModel extends DelegateBlockStateModel {
    private static final Direction[] DIRECTIONS = Arrays.copyOfRange(Direction.values(), 0, 7);
    private static final Map<MuralBlockEntity.MuralData, List<BlockModelPart>> CACHED_PARTS = new ConcurrentHashMap<>();

    private final ResourceLocation originTexture;

    public MuralModel(BlockStateModel delegate) {
        super(delegate);
		this.originTexture = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "block/mural_side");
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

    private @NotNull ImmutableList<BlockModelPart> rebakeModelParts(List<BlockModelPart> blockModelParts, BlockState state, MuralBlockEntity.MuralData data) {
        TextureAtlas atlas = Minecraft.getInstance().getModelManager().getAtlas(TextureAtlas.LOCATION_BLOCKS);
		TextureAtlasSprite defaultSprite = atlas.getSprite(this.originTexture);
		TextureAtlasSprite muralSprite = AetherIIAtlases.getMuralMaterial(data.mural().getKey()).sprite();

        ImmutableList.Builder<BlockModelPart> partBuilder = ImmutableList.builder();
        for (BlockModelPart part : blockModelParts) {
            QuadCollection.Builder builder = new QuadCollection.Builder();
            for (Direction side : DIRECTIONS) {
                for (BakedQuad originalQuad : part.getQuads(side)) {
                    BakedQuad newModelQuad = side != data.facing() ? originalQuad : this.rebakeQuad(data, originalQuad, defaultSprite, muralSprite);
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

    private @NotNull BakedQuad rebakeQuad(MuralBlockEntity.MuralData data, BakedQuad quad, TextureAtlasSprite defaultSprite, TextureAtlasSprite muralSprite) {
        int[] vertices = bakeMuralSection(defaultSprite, muralSprite, data, quad.vertices());

        return new BakedQuad(vertices, quad.tintIndex(), quad.direction(), muralSprite, quad.shade(), quad.lightEmission(), quad.hasAmbientOcclusion());
    }

    private static int[] bakeMuralSection(TextureAtlasSprite originSprite, TextureAtlasSprite muralSprite, MuralBlockEntity.MuralData data, int[] bakedBuffer) {

        // Avoids mutating the original model in-memory
		int[] rebakedSection = Arrays.copyOf(bakedBuffer, bakedBuffer.length);

        // Square limits for the given section of mural inside atlas
        float sectionUMin = data.offsetX() / (float) data.width();
        float sectionUMax = (data.offsetX() + 1) / (float) data.width();
        float sectionVMin = data.offsetY() / (float) data.height();
        float sectionVMax = (data.offsetY() + 1) / (float) data.height();

        for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
            int bufferOffset = vertexIndex * 8;
            // UV coords from model elements
            // Attribute offsets known from DefaultVertexFormat.BLOCK
            float elementU = Float.intBitsToFloat(bakedBuffer[bufferOffset + 4]);
            float elementV = Float.intBitsToFloat(bakedBuffer[bufferOffset + 5]);

            float muralU = Mth.map(elementU, originSprite.getU0(), originSprite.getU1(), sectionUMin, sectionUMax);
            float muralV = Mth.map(elementV, originSprite.getV0(), originSprite.getV1(), sectionVMin, sectionVMax);

            float perBlockMuralU = Mth.lerp(muralU, muralSprite.getU0(), muralSprite.getU1());
            float perBlockMuralV = Mth.lerp(muralV, muralSprite.getV0(), muralSprite.getV1());

            rebakedSection[bufferOffset + 4] = Float.floatToIntBits(perBlockMuralU);
            rebakedSection[bufferOffset + 5] = Float.floatToIntBits(perBlockMuralV);
        }

        return rebakedSection;
    }

    public static void registerReloadListener(AddClientReloadListenersEvent event) {
        // Clear cache as UVs can change from resource packs
        event.addListener(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "mural_cache"), (ResourceManagerReloadListener) resourceManager -> CACHED_PARTS.clear());
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        // Clear stale holders to prevent memory leaks
        CACHED_PARTS.clear();
    }
}
