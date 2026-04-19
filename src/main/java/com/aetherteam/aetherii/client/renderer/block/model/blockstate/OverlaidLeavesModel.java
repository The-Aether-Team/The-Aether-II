package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import net.minecraft.client.Minecraft;
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

import java.util.List;

//public class OverlaidLeavesModel extends DelegateBlockStateModel {
//    public OverlaidLeavesModel(BlockStateModel originalModel) {
//        super(originalModel);
//    }
//
//    @Override
//    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
//        List<BlockState> delegateParts = this.delegate.collectParts(level, pos, state, random);
//        int regularIndex = 2;
//        int baseIndex = 1;
//        int overlayIndex = 0;
//        for (BlockStateModelPart part : delegateParts) {
//            if (part instanceof SimpleModelWrapper simpleModelWrapper) {
//                QuadCollection.Builder baseBuilder = new QuadCollection.Builder();
//                QuadCollection.Builder overlayBuilder = new QuadCollection.Builder();
//                for (Direction direction : Direction.values()) {
//                    List<BakedQuad> quads = simpleModelWrapper.getQuads(direction);
//                    if (quads.size() <= 2) {
//                        if (direction == null) {
//                            baseBuilder.addUnculledFace(quads.getFirst());
//                        } else {
//                            baseBuilder.addCulledFace(direction, quads.getFirst());
//                        }
//                    } else {
//                        if (direction == null) {
//                            baseBuilder.addUnculledFace(quads.get(baseIndex));
//                            overlayBuilder.addUnculledFace(quads.get(overlayIndex));
//                        } else {
//                            BlockState relativeState = level.getBlockState(pos.relative(direction));
//                            if (!relativeState.is(state.getBlock())
//                                    || (relativeState.getValue(AetherLeavesBlock.SNOWY) != state.getValue(AetherLeavesBlock.SNOWY))
//                                    || (relativeState.getValue(AetherLeavesBlock.MOSSY) != state.getValue(AetherLeavesBlock.MOSSY))) {
//                                baseBuilder.addCulledFace(direction, quads.get(baseIndex));
//                                overlayBuilder.addCulledFace(direction, quads.get(overlayIndex));
//                            } else {
//                                overlayBuilder.addCulledFace(direction, quads.get(regularIndex));
//                            }
//                        }
//                    }
//                }
//                QuadCollection baseQuads = baseBuilder.build();
//                QuadCollection overlayQuads = overlayBuilder.build();
//                if (!baseQuads.getAll().isEmpty()) {
//                    parts.add(new SimpleModelWrapper(baseBuilder.build(), simpleModelWrapper.useAmbientOcclusion(), simpleModelWrapper.particleMaterial(), Minecraft.getInstance().options.cutoutLeaves().get() ? ChunkSectionLayer.CUTOUT : ChunkSectionLayer.SOLID));
//                }
//                if (!overlayQuads.getAll().isEmpty()) {
//                    parts.add(new SimpleModelWrapper(overlayBuilder.build(), simpleModelWrapper.useAmbientOcclusion(), simpleModelWrapper.particleMaterial(), ChunkSectionLayer.CUTOUT));
//                }
//            }
//        }
//    }
//}