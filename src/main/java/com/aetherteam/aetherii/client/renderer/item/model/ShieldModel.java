package com.aetherteam.aetherii.client.renderer.item.model;

import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.NeoForgeRenderTypes;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.color.item.FluidContentsTint;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.model.ComposedModelState;
import net.neoforged.neoforge.client.model.QuadTransformers;
import net.neoforged.neoforge.client.model.UnbakedElementsHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

public class ShieldModel implements ItemModel {
    private static final ModelDebugName DEBUG_NAME = () -> "ShieldModel";

    private final ShieldModel.Unbaked unbakedModel;
    private final BakingContext bakingContext;
    private final ItemTransforms itemTransforms;

    private ShieldModel(ShieldModel.Unbaked unbakedModel, BakingContext bakingContext) {
        this.unbakedModel = unbakedModel;
        this.bakingContext = bakingContext;
        var baseItemModel = bakingContext.blockModelBaker().getModel(unbakedModel.parent());
        if (baseItemModel == null) {
            throw new IllegalStateException("Failed to access item/generated model");
        }
        this.itemTransforms = baseItemModel.getTopTransforms();
    }

    @Override
    public void update(ItemStackRenderState renderState, ItemStack stack, ItemModelResolver modelResolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable LivingEntity entity, int p_387820_) {
        this.bake().update(renderState, stack, modelResolver, displayContext, level, entity, p_387820_);
    }

    private ItemModel bake() { //todo index system
        var sprites = this.bakingContext.blockModelBaker().sprites();

        Material front0Location = ClientHooks.getBlockMaterial(this.unbakedModel.textures().front0());
        Material front1Location = ClientHooks.getBlockMaterial(this.unbakedModel.textures().front1());
        Material front2Location = ClientHooks.getBlockMaterial(this.unbakedModel.textures().front2());
        Material front3Location = ClientHooks.getBlockMaterial(this.unbakedModel.textures().front3());
        Material back0Location = ClientHooks.getBlockMaterial(this.unbakedModel.textures().back0());
        Material back1Location = ClientHooks.getBlockMaterial(this.unbakedModel.textures().back1());
        Material back2Location = ClientHooks.getBlockMaterial(this.unbakedModel.textures().back2());
        Material back3Location = ClientHooks.getBlockMaterial(this.unbakedModel.textures().back3());
        Material handleLocation = ClientHooks.getBlockMaterial(this.unbakedModel.textures().handle());

        TextureAtlasSprite front0Sprite = sprites.get(front0Location, DEBUG_NAME);
        TextureAtlasSprite front1Sprite = sprites.get(front1Location, DEBUG_NAME);
        TextureAtlasSprite front2Sprite = sprites.get(front2Location, DEBUG_NAME);
        TextureAtlasSprite front3Sprite = sprites.get(front3Location, DEBUG_NAME);
        TextureAtlasSprite back0Sprite = sprites.get(back0Location, DEBUG_NAME);
        TextureAtlasSprite back1Sprite = sprites.get(back1Location, DEBUG_NAME);
        TextureAtlasSprite back2Sprite = sprites.get(back2Location, DEBUG_NAME);
        TextureAtlasSprite back3Sprite = sprites.get(back3Location, DEBUG_NAME);
        TextureAtlasSprite handleSprite = sprites.get(handleLocation, DEBUG_NAME);

        List<ItemModel> subModels = new ArrayList<>();

        List<BakedQuad> combinedQuads = new ArrayList<>();

        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, front0Sprite), $ -> front0Sprite,
                new ComposedModelState(BlockModelRotation.X0_Y180, new Transformation(new Vector3f(-(8.0F / 16.0F) - (3.0F / 16.0F), (8.0F / 16.0F) - (0.5F / 16.0F), -0.001F - (3.5F / 16.0F)), new Quaternionf(), new Vector3f(1, 1, 1), new Quaternionf()))));
        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, front1Sprite), $ -> front1Sprite,
                new ComposedModelState(BlockModelRotation.X0_Y180, new Transformation(new Vector3f((8.0F / 16.0F) - (3.0F / 16.0F), (8.0F / 16.0F) - (0.5F / 16.0F), -0.001F - (3.5F / 16.0F)), new Quaternionf(), new Vector3f(1, 1, 1), new Quaternionf()))));
        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, front2Sprite), $ -> front2Sprite,
                new ComposedModelState(BlockModelRotation.X0_Y180, new Transformation(new Vector3f(-(8.0F / 16.0F) - (3.0F / 16.0F), -(8.0F / 16.0F) - (0.5F / 16.0F), -0.001F - (3.5F / 16.0F)), new Quaternionf(), new Vector3f(1, 1, 1), new Quaternionf()))));
        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, front3Sprite), $ -> front3Sprite,
                new ComposedModelState(BlockModelRotation.X0_Y180, new Transformation(new Vector3f((8.0F / 16.0F) - (3.0F / 16.0F), -(8.0F / 16.0F) - (0.5F / 16.0F), -0.001F - (3.5F / 16.0F)), new Quaternionf(), new Vector3f(1, 1, 1), new Quaternionf()))));

        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, back0Sprite), $ -> back0Sprite,
                new ComposedModelState(BlockModelRotation.X0_Y180, new Transformation(new Vector3f(-(7.998F / 16.0F) - (3.0F / 16.0F), (7.998F / 16.0F) - (0.5F / 16.0F), 0.001F - (3.5F / 16.0F)), new Quaternionf(), new Vector3f(0.9999F, 0.9999F, 0.9999F), new Quaternionf()))));
        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, back1Sprite), $ -> back1Sprite,
                new ComposedModelState(BlockModelRotation.X0_Y180, new Transformation(new Vector3f((7.998F / 16.0F) - (3.0F / 16.0F), (7.998F / 16.0F) - (0.5F / 16.0F), 0.001F - (3.5F / 16.0F)), new Quaternionf(), new Vector3f(0.9999F, 0.9999F, 0.9999F), new Quaternionf()))));
        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, back2Sprite), $ -> back2Sprite,
                new ComposedModelState(BlockModelRotation.X0_Y180, new Transformation(new Vector3f(-(7.998F / 16.0F) - (3.0F / 16.0F), -(7.998F / 16.0F) - (0.5F / 16.0F), 0.001F - (3.5F / 16.0F)), new Quaternionf(), new Vector3f(0.9999F, 0.9999F, 0.9999F), new Quaternionf()))));
        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, back3Sprite), $ -> back3Sprite,
                new ComposedModelState(BlockModelRotation.X0_Y180, new Transformation(new Vector3f((7.998F / 16.0F) - (3.0F / 16.0F), -(7.998F / 16.0F) - (0.5F / 16.0F), 0.001F - (3.5F / 16.0F)), new Quaternionf(), new Vector3f(0.9999F, 0.9999F, 0.9999F), new Quaternionf()))));

        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, handleSprite), $ -> handleSprite,
                new ComposedModelState(BlockModelRotation.X180_Y90, new Transformation(new Vector3f(0, (0.5F / 16.0F), (3.0F / 16.0F)), new Quaternionf(), new Vector3f(1, 1, 2), new Quaternionf()))));

        subModels.add(new BlockModelWrapper(List.of(), combinedQuads, new ModelRenderProperties(false, handleSprite, this.itemTransforms), NeoForgeRenderTypes.ITEM_UNSORTED_UNLIT_TRANSLUCENT.get())); //todo particle sprite

        return new CompositeModel(subModels);
    }

    public record Textures(ResourceLocation front0, ResourceLocation front1, ResourceLocation front2, ResourceLocation front3, ResourceLocation back0, ResourceLocation back1, ResourceLocation back2, ResourceLocation back3, ResourceLocation handle) {
        public static final Codec<ShieldModel.Textures> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("front_0").forGetter(ShieldModel.Textures::front0),
                ResourceLocation.CODEC.fieldOf("front_1").forGetter(ShieldModel.Textures::front1),
                ResourceLocation.CODEC.fieldOf("front_2").forGetter(ShieldModel.Textures::front2),
                ResourceLocation.CODEC.fieldOf("front_3").forGetter(ShieldModel.Textures::front3),
                ResourceLocation.CODEC.fieldOf("back_0").forGetter(ShieldModel.Textures::back0),
                ResourceLocation.CODEC.fieldOf("back_1").forGetter(ShieldModel.Textures::back1),
                ResourceLocation.CODEC.fieldOf("back_2").forGetter(ShieldModel.Textures::back2),
                ResourceLocation.CODEC.fieldOf("back_3").forGetter(ShieldModel.Textures::back3),
                ResourceLocation.CODEC.fieldOf("handle").forGetter(ShieldModel.Textures::handle)
        ).apply(instance, ShieldModel.Textures::new));
    }

    public record Unbaked(ResourceLocation parent, ShieldModel.Textures textures) implements ItemModel.Unbaked {
        public static final MapCodec<ShieldModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("parent").forGetter(ShieldModel.Unbaked::parent),
                ShieldModel.Textures.CODEC.fieldOf("textures").forGetter(ShieldModel.Unbaked::textures)
        ).apply(instance, ShieldModel.Unbaked::new));

        @Override
        public MapCodec<? extends ItemModel.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(BakingContext bakingContext) {
            return new ShieldModel(this, bakingContext);
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(this.parent());
        }
    }
}
