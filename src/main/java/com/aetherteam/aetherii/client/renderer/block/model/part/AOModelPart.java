package com.aetherteam.aetherii.client.renderer.block.model.part;

import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.TriState;

import javax.annotation.Nullable;
import java.util.List;

public record AOModelPart(QuadCollection quads, TriState ambientOcclusion, Material.Baked particleIcon) implements BlockStateModelPart {
    @Override
    public List<BakedQuad> getQuads(@Nullable Direction direction) {
        return this.quads.getQuads(direction);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public Material.Baked particleMaterial() {
        return this.particleIcon();
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags() {
        return this.quads.materialFlags();
    }
}
