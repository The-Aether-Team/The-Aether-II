package com.aetherteam.aetherii.client.renderer.item.model;

import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.NeoForgeRenderTypes;
import net.neoforged.neoforge.client.model.ComposedModelState;
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
        this.itemTransforms = baseItemModel.getTopTransforms();
    }

    @Override
    public void update(ItemStackRenderState renderState, ItemStack stack, ItemModelResolver modelResolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable LivingEntity entity, int p_387820_) {
        this.bake(displayContext).update(renderState, stack, modelResolver, displayContext, level, entity, p_387820_);
    }

    private ItemModel bake(ItemDisplayContext displayContext) {
        List<BakedQuad> combinedQuads = new ArrayList<>();

        combinedQuads.addAll(this.faceElement(this.sprite(this.unbakedModel.textures().front().get(0)), -8.0F, 8.0F, true));
        combinedQuads.addAll(this.faceElement(this.sprite(this.unbakedModel.textures().front().get(1)), 8.0F, 8.0F, true));
        combinedQuads.addAll(this.faceElement(this.sprite(this.unbakedModel.textures().front().get(2)), -8.0F, -8.0F, true));
        combinedQuads.addAll(this.faceElement(this.sprite(this.unbakedModel.textures().front().get(3)), 8.0F, -8.0F, true));
        combinedQuads.addAll(this.faceElement(this.sprite(this.unbakedModel.textures().back().get(0)), -7.998F, 7.998F, false));
        combinedQuads.addAll(this.faceElement(this.sprite(this.unbakedModel.textures().back().get(1)), 7.998F, 7.998F, false));
        combinedQuads.addAll(this.faceElement(this.sprite(this.unbakedModel.textures().back().get(2)), -7.998F, -7.998F, false));
        combinedQuads.addAll(this.faceElement(this.sprite(this.unbakedModel.textures().back().get(3)), 7.998F, -7.998F, false));

        combinedQuads.addAll(UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, this.sprite(this.unbakedModel.textures().handle())), $ -> this.sprite(this.unbakedModel.textures().handle()),
                new ComposedModelState(BlockModelRotation.X180_Y90, new Transformation(new Vector3f(0, px(0.5F), px(3.0F)), new Quaternionf(), new Vector3f(1, 1, 2), new Quaternionf()))));

        return new CompositeModel(List.of(new BlockModelWrapper(List.of(), combinedQuads, new ModelRenderProperties(true, this.sprite(this.unbakedModel.textures().particle()), this.itemTransforms), displayContext == ItemDisplayContext.GUI ? NeoForgeRenderTypes.ITEM_UNSORTED_UNLIT_TRANSLUCENT.get() : NeoForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get())));
    }

    public TextureAtlasSprite sprite(ResourceLocation location) {
        var sprites = this.bakingContext.blockModelBaker().sprites();
        return sprites.get(ClientHooks.getBlockMaterial(location), DEBUG_NAME);
    }

    public List<BakedQuad> faceElement(TextureAtlasSprite sprite, float xOffset, float yOffset, boolean front) {
        Vector3f scale = front ? new Vector3f(1, 1, 1) : new Vector3f(0.9999F, 0.9999F, 0.9999F);
        return UnbakedElementsHelper.bakeElements(UnbakedElementsHelper.createUnbakedItemElements(0, sprite), $ -> sprite,
                new ComposedModelState(BlockModelRotation.X0_Y0, new Transformation(new Vector3f(px(xOffset) + px(3.0F), px(yOffset) - px(0.5F), (0.001F * (front ? 1.0F : -1.0F)) + px(3.5F)), new Quaternionf(), scale, new Quaternionf())));
    }

    public static float px(float offset) {
        return offset / 16.0F;
    }

    public record Textures(List<ResourceLocation> front, List<ResourceLocation> back, ResourceLocation handle, ResourceLocation particle) {
        public static final Codec<ShieldModel.Textures> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.listOf(4, 4).fieldOf("front").forGetter(ShieldModel.Textures::front),
                ResourceLocation.CODEC.listOf(4, 4).fieldOf("back").forGetter(ShieldModel.Textures::back),
                ResourceLocation.CODEC.fieldOf("handle").forGetter(ShieldModel.Textures::handle),
                ResourceLocation.CODEC.fieldOf("particle").forGetter(ShieldModel.Textures::particle)
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
