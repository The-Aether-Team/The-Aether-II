package com.aetherteam.aetherii.client.renderer.item;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.vertex.PoseStack;
import com.google.common.collect.ImmutableList;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ShieldBakedModel extends BakedModelWrapper<BakedModel> {
    private static final FaceBakery FACE_BAKERY = new FaceBakery();
    private static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();
    private static final ResourceLocation MODEL_LOCATION = new ResourceLocation(AetherII.MODID, "item/shield");

    private final String shieldName;
    @Nullable
    private final BakedModel blockingModel;
    private final ItemOverrides overrides;
    private List<BakedQuad> quads;
    private TextureAtlasSprite particle;

    public ShieldBakedModel(BakedModel originalModel, String shieldName, @Nullable BakedModel blockingModel) {
        super(originalModel);
        this.shieldName = shieldName;
        this.blockingModel = blockingModel;
        this.overrides = blockingModel != null ? new ShieldOverrides(this, blockingModel) : ItemOverrides.EMPTY;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        return side == null ? this.quads() : List.of();
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        return this.getQuads(state, side, rand);
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.particle();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
        return this.particle();
    }

    @Override
    public ItemOverrides getOverrides() {
        return this.overrides;
    }

    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        return List.of(this);
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        this.originalModel.getTransforms().getTransform(cameraTransformType).apply(applyLeftHandTransform, poseStack);
        return this;
    }

    private List<BakedQuad> quads() {
        if (this.quads == null) {
            this.quads = ImmutableList.copyOf(this.bakeShieldQuads());
        }
        return this.quads;
    }

    private TextureAtlasSprite particle() {
        if (this.particle == null) {
            this.particle = this.originalModel.getParticleIcon();
        }
        return this.particle;
    }

    private List<BakedQuad> bakeShieldQuads() {
        List<BakedQuad> combinedQuads = new ArrayList<>();
        combinedQuads.addAll(this.faceElement(this.sprite("item/" + this.shieldName + "_front_0"), -8.0F, 8.0F, true));
        combinedQuads.addAll(this.faceElement(this.sprite("item/" + this.shieldName + "_front_1"), 8.0F, 8.0F, true));
        combinedQuads.addAll(this.faceElement(this.sprite("item/" + this.shieldName + "_front_2"), -8.0F, -8.0F, true));
        combinedQuads.addAll(this.faceElement(this.sprite("item/" + this.shieldName + "_front_3"), 8.0F, -8.0F, true));
        combinedQuads.addAll(this.faceElement(this.sprite("item/" + this.shieldName + "_back_0"), -7.998F, 7.998F, false));
        combinedQuads.addAll(this.faceElement(this.sprite("item/" + this.shieldName + "_back_1"), 7.998F, 7.998F, false));
        combinedQuads.addAll(this.faceElement(this.sprite("item/" + this.shieldName + "_back_2"), -7.998F, -7.998F, false));
        combinedQuads.addAll(this.faceElement(this.sprite("item/" + this.shieldName + "_back_3"), 7.998F, -7.998F, false));
        combinedQuads.addAll(this.handleElement(this.sprite("item/" + this.shieldName + "_handle")));
        return combinedQuads;
    }

    private List<BakedQuad> faceElement(TextureAtlasSprite sprite, float xOffset, float yOffset, boolean front) {
        Vector3f scale = front ? new Vector3f(1.0F, 1.0F, 1.0F) : new Vector3f(0.9999F, 0.9999F, 0.9999F);
        Transformation transformation = new Transformation(
                new Vector3f(px(xOffset) + px(3.0F), px(yOffset) - px(0.5F), (front ? 0.0001F : -0.0001F) + px(3.5F)),
                new Quaternionf(),
                scale,
                new Quaternionf());
        List<BakedQuad> quads = this.bakeSprite(sprite, new SimpleModelState(transformation));
        if (front) {
            quads.removeIf((quad) -> quad.getDirection() != Direction.SOUTH);
        } else {
            quads.removeIf((quad) -> quad.getDirection() == Direction.SOUTH);
        }
        return quads;
    }

    private List<BakedQuad> handleElement(TextureAtlasSprite sprite) {
        Transformation handleTransform = new Transformation(
                new Vector3f(0.0F, px(0.5F), px(3.0F)),
                new Quaternionf(),
                new Vector3f(1.0F, 1.0F, 2.0F),
                new Quaternionf());
        Transformation transformation = BlockModelRotation.by(180, 90).getRotation().compose(handleTransform);
        return this.bakeSprite(sprite, new SimpleModelState(transformation));
    }

    private List<BakedQuad> bakeSprite(TextureAtlasSprite sprite, SimpleModelState state) {
        List<BakedQuad> quads = new ArrayList<>();
        List<BlockElement> elements = ITEM_MODEL_GENERATOR.processFrames(0, "layer0", sprite.contents());
        for (BlockElement element : elements) {
            for (Direction direction : element.faces.keySet()) {
                BlockElementFace face = element.faces.get(direction);
                quads.add(FACE_BAKERY.bakeQuad(element.from, element.to, face, sprite, direction, state, element.rotation, element.shade, MODEL_LOCATION));
            }
        }
        return quads;
    }

    private TextureAtlasSprite sprite(String path) {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(AetherII.MODID, path));
    }

    private static float px(float offset) {
        return offset / 16.0F;
    }

    private static class ShieldOverrides extends ItemOverrides {
        private final BakedModel baseModel;
        private final BakedModel blockingModel;

        private ShieldOverrides(BakedModel baseModel, BakedModel blockingModel) {
            this.baseModel = baseModel;
            this.blockingModel = blockingModel;
        }

        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            if (entity != null && entity.isUsingItem() && ItemStack.isSameItemSameTags(entity.getUseItem(), stack)) {
                return this.blockingModel;
            }
            return this.baseModel;
        }
    }
}
