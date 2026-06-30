package com.aetherteam.aetherii.client.renderer.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.BakedModelWrapper;

import java.util.List;
import java.util.Map;

public class MoaEggBakedModel extends BakedModelWrapper<BakedModel> {
    private final Map<ResourceLocation, BakedModel> models;

    public MoaEggBakedModel(BakedModel originalModel, Map<ResourceLocation, BakedModel> models) {
        super(originalModel);
        this.models = models;
    }

    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        MoaEggType type = AetherIIDataComponents.getOrDefault(itemStack, AetherIIDataComponents.MOA_EGG_TYPE, MoaEggType.defaultType());
        BakedModel feathers = this.models.get(featherModel(type.featherShape(), type.featherColor()));
        BakedModel eyes = this.models.get(eyesModel(type.eyeColor()));
        BakedModel keratin = this.models.get(keratinModel(type.keratinColor()));
        if (feathers != null && eyes != null && keratin != null) {
            return List.of(this.originalModel, feathers, eyes, keratin);
        }
        return List.of(this.originalModel);
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        this.originalModel.getTransforms().getTransform(cameraTransformType).apply(applyLeftHandTransform, poseStack);
        return this;
    }

    public static ResourceLocation featherModel(Moa.FeatherShape shape, Moa.FeatherColor color) {
        return itemModel("moa_egg_" + shape.getSerializedName() + "_" + color.getSerializedName());
    }

    public static ResourceLocation eyesModel(Moa.EyeColor color) {
        return itemModel("moa_egg_eyes_" + color.getSerializedName());
    }

    public static ResourceLocation keratinModel(Moa.KeratinColor color) {
        return itemModel("moa_egg_keratin_" + color.getSerializedName());
    }

    private static ResourceLocation itemModel(String path) {
        return new ResourceLocation(AetherII.MODID, "item/" + path);
    }
}
