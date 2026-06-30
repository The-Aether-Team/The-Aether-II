package com.aetherteam.aetherii.client.renderer.item;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HammerOfDemolitionBakedModel extends BakedModelWrapper<BakedModel> {
    private static final ResourceLocation HELD = itemModel("hammer_of_demolition_held");
    private static final ResourceLocation HELD_RANGED = itemModel("hammer_of_demolition_held_ranged");
    private static final ResourceLocation HEAD = itemModel("hammer_of_demolition_head");
    private static final ResourceLocation HEAD_READY = itemModel("hammer_of_demolition_head_ready");
    private static final ResourceLocation HEAD_DEPLOYED = itemModel("hammer_of_demolition_head_deployed");
    private static final List<ResourceLocation> REQUIRED_MODELS = List.of(HELD, HELD_RANGED, HEAD, HEAD_READY, HEAD_DEPLOYED);

    private final Map<ResourceLocation, BakedModel> models;
    private final boolean holdingShift;
    private final boolean onCooldown;
    private final ItemOverrides overrides;
    private ItemDisplayContext displayContext = ItemDisplayContext.NONE;
    private boolean leftHand = false;

    public HammerOfDemolitionBakedModel(BakedModel originalModel, Map<ResourceLocation, BakedModel> models) {
        this(originalModel, models, false, false, null);
    }

    private HammerOfDemolitionBakedModel(BakedModel originalModel, Map<ResourceLocation, BakedModel> models, boolean holdingShift, boolean onCooldown, @Nullable ItemOverrides overrides) {
        super(originalModel);
        this.models = models;
        this.holdingShift = holdingShift;
        this.onCooldown = onCooldown;
        this.overrides = overrides != null ? overrides : new HammerOverrides(
                this,
                new HammerOfDemolitionBakedModel(originalModel, models, true, false, ItemOverrides.EMPTY),
                new HammerOfDemolitionBakedModel(originalModel, models, false, true, ItemOverrides.EMPTY),
                new HammerOfDemolitionBakedModel(originalModel, models, true, true, ItemOverrides.EMPTY));
    }

    @Override
    public ItemOverrides getOverrides() {
        return this.overrides;
    }

    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        if (!this.isHeldContext()) {
            return List.of(this.originalModel);
        }
        return List.of(this);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        if (!this.isHeldContext()) {
            return super.getQuads(state, side, rand);
        }
        return this.compositeQuads(state, side, rand, ModelData.EMPTY, null);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        if (!this.isHeldContext()) {
            return super.getQuads(state, side, rand, extraData, renderType);
        }
        return this.compositeQuads(state, side, rand, extraData, renderType);
    }

    private List<BakedQuad> compositeQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
        List<ResourceLocation> modelLocations = this.selectedModelLocations();
        BakedModel transformModel = this.models.get(modelLocations.get(0));
        if (transformModel == null) {
            return super.getQuads(state, side, rand, extraData, renderType);
        }

        List<BakedQuad> quads = new ArrayList<>();
        for (int i = 0; i < modelLocations.size(); i++) {
            ResourceLocation modelLocation = modelLocations.get(i);
            BakedModel model = this.models.get(modelLocation);
            if (model == null) {
                return super.getQuads(state, side, rand, extraData, renderType);
            }
            List<BakedQuad> modelQuads = model.getQuads(state, side, rand);
            quads.addAll(i == 0 ? modelQuads : transformQuads(modelQuads, headRotationTransform()));
        }
        return quads;
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        this.displayContext = cameraTransformType;
        this.leftHand = applyLeftHandTransform;
        if (this.isHeldContext()) {
            BakedModel transformModel = this.models.get(this.selectedModelLocations().get(0));
            if (transformModel != null) {
                transformModel.getTransforms().getTransform(cameraTransformType).apply(applyLeftHandTransform, poseStack);
                return this;
            }
        }
        this.originalModel.getTransforms().getTransform(cameraTransformType).apply(applyLeftHandTransform, poseStack);
        return this;
    }

    private boolean isHeldContext() {
        return this.displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                || this.displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                || this.displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                || this.displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    private List<ResourceLocation> selectedModelLocations() {
        if (this.onCooldown) {
            return List.of(HELD_RANGED, HEAD_DEPLOYED);
        } else if (this.holdingShift) {
            return List.of(HELD_RANGED, HEAD_READY);
        } else {
            return List.of(HELD, HEAD);
        }
    }

    public static List<ResourceLocation> requiredModels() {
        return REQUIRED_MODELS;
    }

    private static ResourceLocation itemModel(String path) {
        return new ResourceLocation(AetherII.MODID, "item/" + path);
    }

    private static Matrix4f headRotationTransform() {
        return new Matrix4f()
                .translate(0.5F, 0.5F, 0.5F)
                .rotateY((float) Math.toRadians(90.0F))
                .rotateX((float) Math.toRadians(45.0F))
                .translate(-0.5F, -0.5F, -0.5F);
    }

    private static List<BakedQuad> transformQuads(List<BakedQuad> quads, Matrix4f transform) {
        List<BakedQuad> transformed = new ArrayList<>(quads.size());
        for (BakedQuad quad : quads) {
            transformed.add(transformQuad(quad, transform));
        }
        return transformed;
    }

    private static BakedQuad transformQuad(BakedQuad quad, Matrix4f transform) {
        int[] vertices = Arrays.copyOf(quad.getVertices(), quad.getVertices().length);
        int stride = vertices.length / 4;
        Vector3f position = new Vector3f();
        for (int vertex = 0; vertex < 4; vertex++) {
            int offset = vertex * stride;
            position.set(Float.intBitsToFloat(vertices[offset]), Float.intBitsToFloat(vertices[offset + 1]), Float.intBitsToFloat(vertices[offset + 2]));
            transform.transformPosition(position);
            vertices[offset] = Float.floatToRawIntBits(position.x());
            vertices[offset + 1] = Float.floatToRawIntBits(position.y());
            vertices[offset + 2] = Float.floatToRawIntBits(position.z());
        }
        return new BakedQuad(vertices, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade(), quad.hasAmbientOcclusion());
    }

    private static class HammerOverrides extends ItemOverrides {
        private final BakedModel baseModel;
        private final BakedModel readyModel;
        private final BakedModel deployedModel;
        private final BakedModel readyDeployedModel;

        private HammerOverrides(BakedModel baseModel, BakedModel readyModel, BakedModel deployedModel, BakedModel readyDeployedModel) {
            this.baseModel = baseModel;
            this.readyModel = readyModel;
            this.deployedModel = deployedModel;
            this.readyDeployedModel = readyDeployedModel;
        }

        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            if (entity != null && isHolding(entity, stack)) {
                boolean holdingShift = entity.isShiftKeyDown();
                boolean onCooldown = entity instanceof Player player && player.getCooldowns().getCooldownPercent(stack.getItem(), 0.0F) > 0.01F;
                if (holdingShift && onCooldown) {
                    return this.readyDeployedModel;
                } else if (onCooldown) {
                    return this.deployedModel;
                } else if (holdingShift) {
                    return this.readyModel;
                }
            }
            return this.baseModel;
        }

        private static boolean isHolding(LivingEntity entity, ItemStack stack) {
            return ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack)
                    || ItemStack.isSameItemSameTags(entity.getOffhandItem(), stack);
        }
    }
}
