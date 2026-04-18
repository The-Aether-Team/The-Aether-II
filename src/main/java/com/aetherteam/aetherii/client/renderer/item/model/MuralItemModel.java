package com.aetherteam.aetherii.client.renderer.item.model;

import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.client.AetherIIClientCaches;
//import com.aetherteam.aetherii.client.renderer.block.model.blockstate.MuralModel;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.BlockModelWrapperAccessor;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.NeoForgeModelProperties;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4fc;

import java.util.ArrayList;
import java.util.List;

public class MuralItemModel extends CuboidItemModelWrapper {
    public MuralItemModel(List<ItemTintSource> tints, QuadCollection quads, ModelRenderProperties properties, Matrix4fc transformation) {
        super(tints, quads, properties, transformation);
    }

    @Override
    public void update(ItemStackRenderState renderState, ItemStack stack, ItemModelResolver modelResolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int i) {
        BlockModelWrapperAccessor accessor = (BlockModelWrapperAccessor) this;
        MuralSection section = stack.get(AetherIIDataComponents.MURAL_SECTION);
        List<BakedQuad> quads = AetherIIClientCaches.CACHED_MURAL_ITEM_PARTS.get(section);
        if (section != null) {
            if (quads == null) {
                quads = new ArrayList<>(accessor.aether_ii$getQuads());
                quads.replaceAll((originalQuad) -> {
//                    if (originalQuad.direction() == Direction.NORTH) { //todo
//                        return MuralModel.rebakeQuad(section, originalQuad);
//                    }
                    return originalQuad;
                });
                AetherIIClientCaches.CACHED_MURAL_ITEM_PARTS.put(section, quads);
            }
            renderState.appendModelIdentityElement(section);
        }
        List<BakedQuad> finalQuads = quads;
        accessor.aether_ii$setQuads(finalQuads);
        accessor.aether_ii$setExtents(Suppliers.memoize(() -> computeExtents(finalQuads)));
        super.update(renderState, stack, modelResolver, displayContext, level, owner, i);
    }

    public record Unbaked(Identifier model) implements ItemModel.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Identifier.CODEC.fieldOf("model").forGetter(Unbaked::model)
        ).apply(instance, Unbaked::new));

        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            resolver.markDependency(this.model);
        }

        public ItemModel bake(ItemModel.BakingContext context, Matrix4fc matrix4f) {
            ModelBaker modelbaker = context.blockModelBaker();
            ResolvedModel resolvedmodel = modelbaker.getModel(this.model);
            TextureSlots textureslots = resolvedmodel.getTopTextureSlots();
            List<BakedQuad> list = resolvedmodel.bakeTopGeometry(textureslots, modelbaker, BlockModelRotation.IDENTITY).getAll();
            ModelRenderProperties modelrenderproperties = ModelRenderProperties.fromResolvedModel(modelbaker, resolvedmodel, textureslots);
            ChunkSectionLayerGroup chunkSectionLayerGroup = resolvedmodel.getTopAdditionalProperties().getOptional(NeoForgeModelProperties.TRANSFORM);
            ChunkSectionLayer chunkSectionLayer = chunkSectionLayerGroup == null ? null : chunkSectionLayerGroup.entityItem();
            return new MuralItemModel(List.of(), list, modelrenderproperties, (stack) -> chunkSectionLayer);
        }

        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
