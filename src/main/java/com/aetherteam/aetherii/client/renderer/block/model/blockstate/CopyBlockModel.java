package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.blockentity.LockedBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;
import net.neoforged.neoforge.client.model.quad.BakedColors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyBlockModel extends DelegateBlockStateModel {
    private static final Direction[] DIRECTIONS = Arrays.copyOfRange(Direction.values(), 0, 7);

    public CopyBlockModel(BlockStateModel delegate) {
        super(delegate);
    }

    @Override
    public void collectParts(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, BlockState blockState, RandomSource randomSource, List<BlockStateModelPart> list) {
        LockedBlockEntity.CopyData data = blockAndTintGetter.getModelData(blockPos).get(LockedBlockEntity.CopyData.PROPERTY);
        if (data == null) {
            return;
        }
        BlockState state = data.state();
        List<BlockStateModelPart> copyParts = new ArrayList<>();
        Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(state).collectParts(blockAndTintGetter, blockPos, state, randomSource, copyParts);

        this.collectCopyParts(copyParts, list);
    }

    public void collectCopyParts(List<BlockStateModelPart> baseList, List<BlockStateModelPart> addTo) {
        for (BlockStateModelPart modelPart : baseList) {
            QuadCollection.Builder builder = new QuadCollection.Builder();
            for (Direction side : DIRECTIONS) {
                List<BakedQuad> quads = modelPart.getQuads(side);
                for (BakedQuad oldQuad : quads) {
                    ChunkSectionLayer blockRenderType = oldQuad.materialInfo().lightEmission() > 0 ? ChunkSectionLayer.CUTOUT : ChunkSectionLayer.SOLID;
                    BakedQuad newQuad = new BakedQuad(
                            oldQuad.position0(),
                            oldQuad.position1(),
                            oldQuad.position2(),
                            oldQuad.position3(),
                            oldQuad.packedUV0(),
                            oldQuad.packedUV1(),
                            oldQuad.packedUV2(),
                            oldQuad.packedUV3(),
                            oldQuad.direction(),
                            new BakedQuad.MaterialInfo(oldQuad.materialInfo().sprite(), blockRenderType, Sheets.cutoutBlockItemSheet(), 0, oldQuad.materialInfo().shade(), oldQuad.materialInfo().lightEmission(), oldQuad.materialInfo().ambientOcclusion()),
                            oldQuad.bakedNormals(),
                            new BakedColors.PerQuad(-4276546)
                    );
                    if (side == null) {
                        builder.addUnculledFace(newQuad);
                    } else {
                        builder.addCulledFace(side, newQuad);
                    }
                }
            }
            addTo.add(new SimpleModelWrapper(builder.build(), modelPart.useAmbientOcclusion(), modelPart.particleMaterial()));
        }
    }

    @Override
    public Material.Baked particleMaterial(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        LockedBlockEntity.CopyData data = level.getModelData(pos).get(LockedBlockEntity.CopyData.PROPERTY);
        if (data == null) {
            return super.particleMaterial(level, pos, state);
        }
        BlockState mimicState = data.state();
        return Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(mimicState).particleMaterial(level, pos, mimicState);
    }
}
