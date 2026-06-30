package com.aetherteam.aetherii.client.renderer.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.miscellaneous.glider.AercloudGliderItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AercloudGliderBakedModel extends BakedModelWrapper<BakedModel> {
    private static final List<String> GLIDER_NAMES = List.of(
            "cold_aercloud_glider",
            "golden_aercloud_glider",
            "blue_aercloud_glider",
            "purple_aercloud_glider");

    private final Map<ResourceLocation, BakedModel> models;
    private final String gliderName;
    private final boolean open;
    private final ItemOverrides overrides;
    private ItemDisplayContext displayContext = ItemDisplayContext.NONE;

    public AercloudGliderBakedModel(BakedModel originalModel, Map<ResourceLocation, BakedModel> models, String gliderName) {
        this(originalModel, models, gliderName, false, null);
    }

    private AercloudGliderBakedModel(BakedModel originalModel, Map<ResourceLocation, BakedModel> models, String gliderName, boolean open, @Nullable ItemOverrides overrides) {
        super(originalModel);
        this.models = models;
        this.gliderName = gliderName;
        this.open = open;
        this.overrides = overrides != null ? overrides : new GliderOverrides(
                this,
                new AercloudGliderBakedModel(originalModel, models, gliderName, true, ItemOverrides.EMPTY));
    }

    @Override
    public ItemOverrides getOverrides() {
        return this.overrides;
    }

    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        return this.isHeldContext() ? List.of(this) : List.of(this.originalModel);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        BakedModel selectedModel = this.selectedModel();
        return selectedModel != null ? selectedModel.getQuads(state, side, rand) : super.getQuads(state, side, rand);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        BakedModel selectedModel = this.selectedModel();
        return selectedModel != null ? selectedModel.getQuads(state, side, rand, extraData, renderType) : super.getQuads(state, side, rand, extraData, renderType);
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        this.displayContext = cameraTransformType;
        BakedModel selectedModel = this.selectedModel();
        if (selectedModel != null) {
            selectedModel.getTransforms().getTransform(cameraTransformType).apply(applyLeftHandTransform, poseStack);
            return this;
        }
        this.originalModel.getTransforms().getTransform(cameraTransformType).apply(applyLeftHandTransform, poseStack);
        return this;
    }

    private BakedModel selectedModel() {
        if (!this.isHeldContext()) {
            return this.originalModel;
        }
        BakedModel selectedModel = this.models.get(itemModel(this.gliderName + (this.open ? "_open" : "_closed")));
        return selectedModel != null ? selectedModel : this.originalModel;
    }

    private boolean isHeldContext() {
        return this.displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                || this.displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                || this.displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                || this.displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    public static List<String> gliderNames() {
        return GLIDER_NAMES;
    }

    public static List<ResourceLocation> requiredModels() {
        List<ResourceLocation> modelLocations = new ArrayList<>();
        for (String gliderName : GLIDER_NAMES) {
            modelLocations.add(itemModel(gliderName + "_closed"));
            modelLocations.add(itemModel(gliderName + "_open"));
        }
        return modelLocations;
    }

    private static ResourceLocation itemModel(String path) {
        return new ResourceLocation(AetherII.MODID, "item/" + path);
    }

    private static class GliderOverrides extends ItemOverrides {
        private final BakedModel baseModel;
        private final BakedModel openModel;

        private GliderOverrides(BakedModel baseModel, BakedModel openModel) {
            this.baseModel = baseModel;
            this.openModel = openModel;
        }

        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            if (entity != null
                    && entity.isUsingItem()
                    && entity.getUseItem().getItem() instanceof AercloudGliderItem
                    && ItemStack.isSameItemSameTags(entity.getUseItem(), stack)) {
                return this.openModel;
            }
            return this.baseModel;
        }
    }
}
