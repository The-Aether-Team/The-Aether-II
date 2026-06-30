package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class AccessoryLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final HumanoidModel<T> accessoryModel;

    public AccessoryLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.accessoryModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.ACCESSORY));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        ItemStack stack = AccessoryUtil.getFirst(entity, AccessoryContainer.SlotType.ACCESSORY).orElse(ItemStack.EMPTY);
        if (stack.isEmpty()) {
            return;
        }

        this.getParentModel().copyPropertiesTo(this.accessoryModel);
        ResourceLocation itemLocation = BuiltInRegistries.ITEM.getKey(stack.getItem());
        ResourceLocation texture = new ResourceLocation(itemLocation.getNamespace(), "textures/entity/equipment/accessory/" + itemLocation.getPath() + ".png");
        VertexConsumer consumer = buffer.getBuffer(RenderType.armorCutoutNoCull(texture));
        this.accessoryModel.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
