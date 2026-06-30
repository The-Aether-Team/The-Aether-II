package com.aetherteam.aetherii.client.renderer.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.BakedModelWrapper;

import java.util.List;
import java.util.Map;

public class MoaFeatherBakedModel extends BakedModelWrapper<BakedModel> {
    private final Map<ResourceLocation, BakedModel> models;

    public MoaFeatherBakedModel(BakedModel originalModel, Map<ResourceLocation, BakedModel> models) {
        super(originalModel);
        this.models = models;
    }

    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        Moa.FeatherColor color = AetherIIDataComponents.getOrDefault(itemStack, AetherIIDataComponents.FEATHER_COLOR, Moa.FeatherColor.DEFAULT);
        BakedModel feather = this.models.get(featherModel(color));
        if (feather != null) {
            return List.of(feather);
        }
        return List.of(this.originalModel);
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        this.originalModel.getTransforms().getTransform(cameraTransformType).apply(applyLeftHandTransform, poseStack);
        return this;
    }

    public static List<ResourceLocation> requiredModels() {
        return Moa.FeatherColor.stream(true).map(MoaFeatherBakedModel::featherModel).toList();
    }

    public static ResourceLocation featherModel(Moa.FeatherColor color) {
        return itemModel("moa_feather_" + color.getSerializedName());
    }

    private static ResourceLocation itemModel(String path) {
        return new ResourceLocation(AetherII.MODID, "item/" + path);
    }
}
