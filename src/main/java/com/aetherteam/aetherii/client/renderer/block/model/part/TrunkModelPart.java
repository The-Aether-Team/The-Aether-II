package com.aetherteam.aetherii.client.renderer.block.model.part;

import com.aetherteam.aetherii.client.renderer.block.model.state.TrunkModelState;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public record TrunkModelPart(QuadCollection quads, boolean useAmbientOcclusion, TextureAtlasSprite particleIcon) implements BlockModelPart { //todo
    @Override
    public List<BakedQuad> getQuads(@Nullable Direction direction) {
        return this.quads.getQuads(direction);
    }

    public record Unbaked(ResourceLocation modelLocation, TrunkModelState modelState) implements BlockModelPart.Unbaked {
        public static final MapCodec<Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        ResourceLocation.CODEC.fieldOf("model").forGetter(TrunkModelPart.Unbaked::modelLocation),
                        TrunkModelState.CODEC.fieldOf("state").forGetter(TrunkModelPart.Unbaked::modelState)
                ).apply(instance, TrunkModelPart.Unbaked::new)
        );

        @Override
        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            resolver.markDependency(this.modelLocation);
        }

        @Override
        public BlockModelPart bake(ModelBaker baker) {
            ResolvedModel resolvedModel = baker.getModel(this.modelLocation);

            TextureSlots slots = resolvedModel.getTopTextureSlots();
            boolean ao = resolvedModel.getTopAmbientOcclusion();
            TextureAtlasSprite particle = resolvedModel.resolveParticleSprite(slots, baker);
            QuadCollection quads = resolvedModel.bakeTopGeometry(slots, baker, this.modelState);

            return new TrunkModelPart(quads, ao, particle);
        }
    }
}
