package com.aetherteam.aetherii.client.renderer.item.model;

import java.util.Set;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.client.renderer.blockentity.MuralRenderer;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class MuralSpecialRenderer implements SpecialModelRenderer<MuralSection> {
    private final MuralRenderer muralRenderer;

    public MuralSpecialRenderer(MuralRenderer muralRenderer) {
        this.muralRenderer = muralRenderer;
    }

    @Override
    @Nullable
    public MuralSection extractArgument(ItemStack stack) {
        return stack.get(AetherIIDataComponents.MURAL_SECTION);
    }

    @Override
    public void render(@Nullable MuralSection patterns, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoilType) {
        this.muralRenderer.renderInHand(poseStack, bufferSource, packedLight, packedOverlay, patterns);
    }

    @Override
    public void getExtents(Set<Vector3f> output) {
        this.muralRenderer.getExtents(output);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<MuralSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new MuralSpecialRenderer.Unbaked());

        @Override
        public MapCodec<? extends net.minecraft.client.renderer.special.SpecialModelRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet modelSet) {
            return new MuralSpecialRenderer(new MuralRenderer(modelSet));
        }
    }
}
