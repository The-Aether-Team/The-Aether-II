package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.blockentity.MuralBlockEntity;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.block.model.part.MuralModelPart;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;

import java.util.Arrays;
import java.util.List;

public class MuralModel extends DelegateBlockStateModel {
    public MuralModel(BlockStateModel delegate) {
        super(delegate);
    }

    private static final Direction[] DIRECTIONS = Arrays.copyOfRange(Direction.values(), 0, 7);

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        MuralBlockEntity.MuralData data = level.getModelData(pos).get(MuralBlockEntity.MuralData.PROPERTY);
        if (data == null) {
            super.collectParts(level, pos, state, random, parts);
            return;
        }
        for (BlockModelPart part : this.delegate.collectParts(level, pos, state, random)) {
            QuadCollection.Builder builder = new QuadCollection.Builder();
            for (Direction side : DIRECTIONS) {
                if (side == data.facing()) {

                    for (BakedQuad quad : part.getQuads(side)) {
                        Material material = AetherIIAtlases.getMuralMaterial(data.mural().getKey());
                        AetherII.LOGGER.info(String.valueOf(material));
                        TextureAtlasSprite sprite = material.sprite();
                        TextureAtlasSprite newSprite = new TextureAtlasSprite(sprite.atlasLocation(), sprite.contents(), sprite.contents().width(), sprite.contents().height(), data.offsetX(), data.offsetY()); //todo still using old texture for some reason

                        BakedQuad newQuad = new BakedQuad(quad.vertices(), quad.tintIndex(), quad.direction(), newSprite, quad.shade(), quad.lightEmission(), quad.hasAmbientOcclusion());
                        if (side == null) {
                            builder.addUnculledFace(newQuad);
                        } else {
                            builder.addCulledFace(side, newQuad);
                        }
                    }
                } else {
                    for (BakedQuad quad : part.getQuads(side)) {
                        if (side == null) {
                            builder.addUnculledFace(quad);
                        } else {
                            builder.addCulledFace(side, quad);
                        }
                    }
                }
            }
            parts.add(new SimpleModelWrapper(builder.build(), part.useAmbientOcclusion(), part.particleIcon(), part.getRenderType(state)));
        }
    }
}
