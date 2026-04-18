package com.aetherteam.aetherii.client.renderer.item.model;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.NeoForgeModelProperties;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4fc;

import java.util.List;
import java.util.function.Function;

public class EmissiveModel extends CuboidItemModelWrapper {
    public EmissiveModel(List<ItemTintSource> tints, QuadCollection quads, ModelRenderProperties properties, Matrix4fc transformation) {
        super(List.of(), quads, properties, transformation);
    }

    public record Unbaked(Identifier model) implements ItemModel.Unbaked {
        public static final MapCodec<EmissiveModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Identifier.CODEC.fieldOf("model").forGetter(EmissiveModel.Unbaked::model)
        ).apply(instance, EmissiveModel.Unbaked::new));

        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            resolver.markDependency(this.model);
        }

        public ItemModel bake(ItemModel.BakingContext context, Matrix4fc matrix4f) {
            ModelBaker modelbaker = context.blockModelBaker();
            ResolvedModel resolvedmodel = modelbaker.getModel(this.model);
            TextureSlots textureslots = resolvedmodel.getTopTextureSlots();
            List<BakedQuad> list = resolvedmodel.bakeTopGeometry(textureslots, modelbaker, BlockModelRotation.IDENTITY).getAll();
//            list = list.stream().map(quad -> new BakedQuad(quad.vertices(), quad.tintIndex(), quad.direction(), quad.sprite(), quad.shade(), 15, quad.hasAmbientOcclusion())).toList(); //todo
            ModelRenderProperties modelrenderproperties = ModelRenderProperties.fromResolvedModel(modelbaker, resolvedmodel, textureslots);
            ChunkSectionLayerGroup chunkSectionLayerGroup = resolvedmodel.getTopAdditionalProperties().getOptional(NeoForgeModelProperties.TRANSFORM);
            ChunkSectionLayer chunkSectionLayer = chunkSectionLayerGroup == null ? null : chunkSectionLayerGroup.entityItem();
            return new EmissiveModel(list, modelrenderproperties, (stack) -> chunkSectionLayer);
        }

        public MapCodec<EmissiveModel.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
