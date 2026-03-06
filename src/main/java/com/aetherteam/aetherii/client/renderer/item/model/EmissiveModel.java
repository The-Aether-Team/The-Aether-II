package com.aetherteam.aetherii.client.renderer.item.model;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.model.NeoForgeModelProperties;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class EmissiveModel extends BlockModelWrapper {
    public EmissiveModel(List<BakedQuad> quads, ModelRenderProperties properties, @Nullable Function<ItemStack, RenderType> renderTypes) {
        super(List.of(), quads, properties, renderTypes);
    }

    public record Unbaked(Identifier model) implements ItemModel.Unbaked {
        public static final MapCodec<EmissiveModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Identifier.CODEC.fieldOf("model").forGetter(EmissiveModel.Unbaked::model)
        ).apply(instance, EmissiveModel.Unbaked::new));

        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            resolver.markDependency(this.model);
        }

        public ItemModel bake(ItemModel.BakingContext context) {
            ModelBaker modelbaker = context.blockModelBaker();
            ResolvedModel resolvedmodel = modelbaker.getModel(this.model);
            TextureSlots textureslots = resolvedmodel.getTopTextureSlots();
            List<BakedQuad> list = resolvedmodel.bakeTopGeometry(textureslots, modelbaker, BlockModelRotation.IDENTITY).getAll();
//            list = list.stream().map(quad -> new BakedQuad(quad.vertices(), quad.tintIndex(), quad.direction(), quad.sprite(), quad.shade(), 15, quad.hasAmbientOcclusion())).toList(); //todo
            ModelRenderProperties modelrenderproperties = ModelRenderProperties.fromResolvedModel(modelbaker, resolvedmodel, textureslots);
            RenderTypeGroup renderTypeGroup = resolvedmodel.getTopAdditionalProperties().getOptional(NeoForgeModelProperties.RENDER_TYPE);
            RenderType renderType = renderTypeGroup == null ? null : renderTypeGroup.entityItem();
            return new EmissiveModel(list, modelrenderproperties, (stack) -> renderType);
        }

        public MapCodec<EmissiveModel.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
