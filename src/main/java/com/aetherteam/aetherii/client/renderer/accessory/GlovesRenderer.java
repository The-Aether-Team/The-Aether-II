package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.item.equipment.armor.AetherIIArmorMaterials;
import com.aetherteam.aetherii.item.equipment.armor.GlovesItem;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.client.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class GlovesRenderer implements SimpleAccessoryRenderer {
    private final GlovesModel glovesModel;
    private final GlovesModel glovesModelSlim;

    public GlovesRenderer() {
        this.glovesModel = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES));
        this.glovesModelSlim = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_SLIM));
    }

    @Override
    public <S extends LivingEntityRenderState> void render(ItemStack stack, SlotReference reference, PoseStack poseStack, EntityModel<S> model, S renderState, MultiBufferSource buffer, int packedLight, float partialTicks) {
        GlovesItem glovesItem = (GlovesItem) stack.getItem();
        GlovesModel glovesModel = this.glovesModel;
        ResourceLocation texture = glovesItem.getGlovesTexture();

        if (model instanceof PlayerModel playerModel) {
            PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
            glovesModel = playerModelAccessor.aether$getSlim() ? this.glovesModelSlim : this.glovesModel;
        }

        this.align(stack, reference, (EntityModel<S>) glovesModel, renderState, poseStack);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(texture));
        glovesModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);

        if (stack.is(ItemTags.DYEABLE)) {
            IClientItemExtensions extensions = IClientItemExtensions.of(stack);
            int i = extensions.getDefaultDyeColor(stack);
            int color = ARGB.opaque(DyedItemColor.getOrDefault(stack, i));
            ResourceLocation dyedTexture = ResourceLocation.parse(glovesItem.getGlovesTexture().toString().replace(".png", "_dyed.png"));
            VertexConsumer dyedConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(dyedTexture));
            glovesModel.renderToBuffer(poseStack, dyedConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
        }

        if (stack.hasFoil()) {
            glovesModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.armorEntityGlint()), packedLight, OverlayTexture.NO_OVERLAY, -1);
        }
    }

    @Override
    public <S extends LivingEntityRenderState> void align(ItemStack stack, SlotReference reference, EntityModel<S> model, S renderState, PoseStack poseStack) {
        if (model instanceof HumanoidModel<? extends HumanoidRenderState> humanoidModel) {
            AccessoryRenderer.followBodyRotations(reference.entity(), (HumanoidModel<HumanoidRenderState>) humanoidModel);
        }
    }
}