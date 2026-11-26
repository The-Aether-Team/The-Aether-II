package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.ArcticSnowLayerBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BlockQuadOutput;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.neoforged.neoforge.client.event.AddSectionGeometryEvent;

public class SnowSectionRenderer implements AddSectionGeometryEvent.AdditionalSectionRenderer {
    private final BlockPos origin;
    private final LevelChunk chunk;
    private final LevelChunkSection section;

    public SnowSectionRenderer(BlockPos origin, LevelChunk chunk, LevelChunkSection section) {
        this.origin = origin;
        this.chunk = chunk;
        this.section = section;
    }

    @Override
    public void render(AddSectionGeometryEvent.SectionRenderingContext context) {
        ModelBlockRenderer blockRenderer = context.getBlockRenderer();
        BlockAndTintGetter region = context.getRegion();
        BlockPos minPos = this.origin;
        BlockPos maxPos = minPos.offset(15, 15, 15);

        BlockQuadOutput quadOutput = (x, y, z, quad, instance) -> {
            VertexConsumer consumer = context.getOrCreateChunkBuffer(quad.materialInfo().layer());
            consumer.putBlockBakedQuad(x, y, z, quad, instance);
        };
        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            BlockState state = region.getBlockState(pos);
            if (AetherGrassBlock.plantIsSnowed(state)) {
                BlockState snow = AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState().setValue(ArcticSnowLayerBlock.LAYERS, state.getValue(Snowable.SNOW_LAYERS));

                blockRenderer.tesselateBlock(
                        quadOutput,
                        SectionPos.sectionRelative(pos.getX()),
                        SectionPos.sectionRelative(pos.getY()),
                        SectionPos.sectionRelative(pos.getZ()),
                        region,
                        pos,
                        snow,
                        Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(snow),
                        snow.getSeed(pos)
                );
            }
        }
    }
}
