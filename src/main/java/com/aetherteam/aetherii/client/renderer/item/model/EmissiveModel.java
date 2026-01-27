package com.aetherteam.aetherii.client.renderer.item.model;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.model.NeoForgeModelProperties;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmissiveModel extends BlockModelWrapper {
    public EmissiveModel(List<BakedQuad> quads, ModelRenderProperties properties, @Nullable RenderType renderType) {
        super(List.of(), quads, properties, renderType);
    }

    public record Unbaked(ResourceLocation model) implements ItemModel.Unbaked {
        public static final MapCodec<EmissiveModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                ResourceLocation.CODEC.fieldOf("model").forGetter(EmissiveModel.Unbaked::model)
        ).apply(instance, EmissiveModel.Unbaked::new));

        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            resolver.markDependency(this.model);
        }

        public ItemModel bake(ItemModel.BakingContext context) {
            ModelBaker modelbaker = context.blockModelBaker();
            ResolvedModel resolvedmodel = modelbaker.getModel(this.model);
            TextureSlots textureslots = resolvedmodel.getTopTextureSlots();
            List<BakedQuad> list = resolvedmodel.bakeTopGeometry(textureslots, modelbaker, BlockModelRotation.X0_Y0).getAll();
            list = list.stream().map(quad -> new BakedQuad(quad.vertices(), quad.tintIndex(), quad.direction(), quad.sprite(), quad.shade(), 15, quad.hasAmbientOcclusion())).toList();
            ModelRenderProperties modelrenderproperties = ModelRenderProperties.fromResolvedModel(modelbaker, resolvedmodel, textureslots);
            RenderTypeGroup renderTypeGroup = resolvedmodel.getTopAdditionalProperties().getOptional(NeoForgeModelProperties.RENDER_TYPE);
            RenderType renderType = renderTypeGroup == null ? null : renderTypeGroup.entity();
            return new EmissiveModel(list, modelrenderproperties, renderType);
        }

        public MapCodec<EmissiveModel.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
