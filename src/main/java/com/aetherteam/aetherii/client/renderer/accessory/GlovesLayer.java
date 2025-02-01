package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ArmorStyle;
import com.aetherteam.aetherii.item.equipment.armor.GlovesItem;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
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

public class GlovesLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>> extends RenderLayer<S, M> {
    private static final Function<ArmorStyle.SpriteKey, TextureAtlasSprite> ARMOR_STYLE_SPRITE_LOOKUP = Util.memoize((key) -> Minecraft.getInstance().getModelManager().getAtlas(AetherIIAtlases.ARMOR_STYLES_SHEET).getSprite(key.textureId()));
    private static final GlovesModel GLOVES_MODEL = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES));
    private static final GlovesModel GLOVES_MODEL_SLIM = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_SLIM));
    private static final GlovesModel GLOVES_MODEL_FIRST_PERSON = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_FIRST_PERSON));

    public GlovesLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, S state, float netHeadYaw, float headPitch) {
        if (Minecraft.getInstance().player != null) {
            ItemStack stack = Minecraft.getInstance().player.getData(AetherIIDataAttachments.ACCESSORIES).getAccessory(AccessoryContainer.HANDWEAR_SLOT).getFirst();
            if (!stack.isEmpty()) {
                GlovesModel glovesModel = GLOVES_MODEL;
                if (this.getParentModel() instanceof HumanoidModel humanoidModel) {
                    if (humanoidModel instanceof PlayerModel playerModel) {
                        PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
                        glovesModel = playerModelAccessor.aether$getSlim() ? GLOVES_MODEL_SLIM : GLOVES_MODEL;
                    }
                    humanoidModel.copyPropertiesTo(glovesModel);
                }
                glovesModel.setAllVisible(false);
                glovesModel.leftArm.visible = true;
                glovesModel.rightArm.visible = true;

                renderGloves(stack, glovesModel, poseStack, buffer, packedLight);
            }
        }
    }

    public static <S extends LivingEntityRenderState> void renderOnFirstPerson(PoseStack poseStack, MultiBufferSource buffer, ItemStack stack, HumanoidArm arm, PlayerSkin skin, int packedLight) {
        if (!stack.isEmpty()) {
            GlovesModel glovesModel = GLOVES_MODEL_FIRST_PERSON;
            glovesModel.setAllVisible(false);

            ModelPart gloveArm = arm == HumanoidArm.RIGHT ? glovesModel.rightArm : glovesModel.leftArm;
            float f = arm != HumanoidArm.LEFT ? 1.0F : -1.0F;

            gloveArm.resetPose();
            gloveArm.visible = true;
            gloveArm.zRot = f * 0.1F;

            float offset = skin.model() == PlayerSkin.Model.SLIM ? 0.0425F : 0.0F;
            poseStack.translate((f * offset) - 0.0025, 0.0025, -0.0025);

            renderGloves(stack, glovesModel, poseStack, buffer, packedLight);
        }
    }

    private static void renderGloves(ItemStack stack, GlovesModel glovesModel, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
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
            TextureAtlasSprite sprite = ARMOR_STYLE_SPRITE_LOOKUP.apply(new ArmorStyle.SpriteKey(Minecraft.getInstance().level.registryAccess(), style, "humanoid_gloves"));
            VertexConsumer consumer = sprite.wrap(buffer.getBuffer(RenderType.armorCutoutNoCull(AetherIIAtlases.ARMOR_STYLES_SHEET)));
            glovesModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
