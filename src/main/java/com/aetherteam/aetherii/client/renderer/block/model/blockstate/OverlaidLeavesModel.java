package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;
import net.neoforged.neoforge.client.model.quad.BakedColors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OverlaidLeavesModel extends DelegateBlockStateModel {
    private static final Direction[] DIRECTIONS = Arrays.copyOfRange(Direction.values(), 0, 7);

    public OverlaidLeavesModel(BlockStateModel originalModel) {
        super(originalModel);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        List<BlockStateModelPart> newParts = new ArrayList<>();
        this.delegate.collectParts(level, pos, state, random, newParts);
        for (BlockStateModelPart part : newParts) {
            QuadCollection.Builder builder = new QuadCollection.Builder();
            for (Direction direction : DIRECTIONS) {
                List<BakedQuad> quads = part.getQuads(direction);
                if (direction != null && direction.getAxis().isHorizontal()) {
                    if (quads.size() >= 2) {
                        int baseIndex = 0;
                        int defaultIndex = 1;
                        int overlayIndex = 2;
                        BlockState relativeState = level.getBlockState(pos.relative(direction));
                        if (!relativeState.is(state.getBlock())
                                || (relativeState.getValue(AetherLeavesBlock.SNOWY) != state.getValue(AetherLeavesBlock.SNOWY))
                                || (relativeState.getValue(AetherLeavesBlock.MOSSY) != state.getValue(AetherLeavesBlock.MOSSY))) {
                            builder.addCulledFace(direction, quads.get(baseIndex));
//                            BakedQuad oldOverlayQuad = quads.get(overlayIndex);
//                            BakedQuad newOverlayQuad = new BakedQuad(
//                                    oldOverlayQuad.position0(),
//                                    oldOverlayQuad.position1(),
//                                    oldOverlayQuad.position2(),
//                                    oldOverlayQuad.position3(),
//                                    oldOverlayQuad.packedUV0(),
//                                    oldOverlayQuad.packedUV1(),
//                                    oldOverlayQuad.packedUV2(),
//                                    oldOverlayQuad.packedUV3(),
//                                    oldOverlayQuad.direction(),
//                                    new BakedQuad.MaterialInfo(oldOverlayQuad.materialInfo().sprite(), ChunkSectionLayer.CUTOUT, Sheets.cutoutBlockItemSheet(), oldOverlayQuad.materialInfo().tintIndex(), oldOverlayQuad.materialInfo().shade(), oldOverlayQuad.materialInfo().lightEmission(), oldOverlayQuad.materialInfo().ambientOcclusion()),
//                                    oldOverlayQuad.bakedNormals(),
//                                    oldOverlayQuad.bakedColors()
//                            );
//                            builder.addCulledFace(direction, newOverlayQuad);
                            builder.addCulledFace(direction, quads.get(overlayIndex));
                        } else {
                            builder.addCulledFace(direction, quads.get(defaultIndex));
                        }
                    } else {
                        for (BakedQuad quad : quads) {
                            builder.addCulledFace(direction, quad);
                        }
                    }
                } else if (direction != null && direction.getAxis().isVertical()) {
                    for (BakedQuad quad : quads) {
                        builder.addCulledFace(direction, quad);
                    }
                } else {
                    for (BakedQuad quad : quads) {
                        builder.addUnculledFace(quad);
                    }
                }
            }
            QuadCollection collection = builder.build();
            if (!collection.getAll().isEmpty()) {
                parts.add(new SimpleModelWrapper(builder.build(), part.useAmbientOcclusion(), part.particleMaterial()));
            }
        }
    }
}