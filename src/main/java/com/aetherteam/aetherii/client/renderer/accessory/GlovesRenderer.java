package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ArmorStyle;
import com.aetherteam.aetherii.item.equipment.armor.GlovesItem;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Function;

public class GlovesRenderer implements AccessoryRenderer {
    private final Function<ArmorStyle.SpriteKey, TextureAtlasSprite> armorStyleSpriteLookup = Util.memoize((key) -> Minecraft.getInstance().getModelManager().getAtlas(AetherIIAtlases.ARMOR_STYLES_SHEET).getSprite(key.textureId()));
    private final GlovesModel glovesModel;
    private final GlovesModel glovesModelSlim;
    private final GlovesModel glovesModelFirstPerson;

    public GlovesRenderer() {
        this.glovesModel = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES));
        this.glovesModelSlim = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_SLIM));
        this.glovesModelFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_FIRST_PERSON));
    }

    @Override
    public <S extends LivingEntityRenderState> void render(ItemStack stack, SlotReference reference, PoseStack poseStack, EntityModel<S> model, S renderState, MultiBufferSource buffer, int packedLight, float partialTicks) {
        GlovesModel glovesModel = this.glovesModel;
        if (model instanceof HumanoidModel humanoidModel) {
            if (humanoidModel instanceof PlayerModel playerModel) {
                PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
                glovesModel = playerModelAccessor.aether$getSlim() ? this.glovesModelSlim : this.glovesModel;
            }
            humanoidModel.copyPropertiesTo(glovesModel);
        }
        glovesModel.setAllVisible(false);
        glovesModel.leftArm.visible = true;
        glovesModel.rightArm.visible = true;

        this.renderGloves(stack, glovesModel, poseStack, buffer, packedLight);
    }

    @Override
    public <S extends LivingEntityRenderState> void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, SlotReference reference, PoseStack poseStack, EntityModel<S> model, S renderState, MultiBufferSource buffer, int packedLight, float partialTicks) {
        if (renderState instanceof PlayerRenderState playerRenderState) {
            GlovesModel glovesModel = this.glovesModelFirstPerson;
            glovesModel.setAllVisible(false);

            ModelPart gloveArm = arm == HumanoidArm.RIGHT ? glovesModel.rightArm : glovesModel.leftArm;
            float f = arm != HumanoidArm.LEFT ? 1.0F : -1.0F;

            gloveArm.resetPose();
            gloveArm.visible = true;
            gloveArm.zRot = f * 0.1F;

            float offset = playerRenderState.skin.model() == PlayerSkin.Model.SLIM ? 0.0425F : 0.0F;
            poseStack.translate((f * offset) - 0.0025, 0.0025, -0.0025);

            this.renderGloves(stack, glovesModel, poseStack, buffer, packedLight);
        }
    }

    private <S extends LivingEntityRenderState> void renderGloves(ItemStack stack, GlovesModel glovesModel, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        ResourceLocation texture = ((GlovesItem) stack.getItem()).getGlovesTexture();
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), stack.hasFoil());

        glovesModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);

        if (stack.is(ItemTags.DYEABLE)) {
            IClientItemExtensions extensions = IClientItemExtensions.of(stack);
            int color = ARGB.opaque(extensions.getDefaultDyeColor(stack));
            VertexConsumer dyedConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
            glovesModel.renderToBuffer(poseStack, dyedConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
        }

        ArmorStyle style = stack.get(AetherIIDataComponents.ARMOR_STYLE);
        if (style != null && Minecraft.getInstance().level != null) {
            TextureAtlasSprite sprite = this.armorStyleSpriteLookup.apply(new ArmorStyle.SpriteKey(Minecraft.getInstance().level.registryAccess(), style, "humanoid_gloves"));
            VertexConsumer consumer = sprite.wrap(buffer.getBuffer(RenderType.armorCutoutNoCull(AetherIIAtlases.ARMOR_STYLES_SHEET)));
            glovesModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }

    @Override
    public boolean shouldRenderInFirstPerson(HumanoidArm arm, ItemStack stack, SlotReference reference) {
        return true;
    }
}