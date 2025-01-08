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
import io.wispforest.accessories.api.client.SimpleAccessoryRenderer;
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
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
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
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Function;

public class GlovesRenderer implements SimpleAccessoryRenderer {
    private final Function<ArmorStyle.SpriteKey, TextureAtlasSprite> armorStyleSpriteLookup = Util.memoize((key) -> Minecraft.getInstance().getModelManager().getAtlas(AetherIIAtlases.ARMOR_STYLES_SHEET).getSprite(key.textureId()));
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
        glovesModel.setAllVisible(false);
        glovesModel.leftArm.visible = true;
        glovesModel.rightArm.visible = true;

        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
        glovesModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);

        if (stack.is(ItemTags.DYEABLE)) {
            IClientItemExtensions extensions = IClientItemExtensions.of(stack);
            int i = extensions.getDefaultDyeColor(stack);
            int color = ARGB.opaque(i);
            ResourceLocation dyedTexture = ResourceLocation.parse(glovesItem.getGlovesTexture().toString().replace(".png", "_dyed.png"));
            VertexConsumer dyedConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(dyedTexture), stack.hasFoil());
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
    public <S extends LivingEntityRenderState> void align(ItemStack stack, SlotReference reference, EntityModel<S> model, S renderState, PoseStack poseStack) {
        if (model instanceof HumanoidModel<? extends HumanoidRenderState> humanoidModel) {
            AccessoryRenderer.followBodyRotations(reference.entity(), (HumanoidModel<HumanoidRenderState>) humanoidModel);
        }
    }

    @Override
    public boolean shouldRenderInFirstPerson(HumanoidArm arm, ItemStack stack, SlotReference reference) {
        return true;
    }

    @Override
    public <S extends LivingEntityRenderState> void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, SlotReference reference, PoseStack poseStack, EntityModel<S> model, S renderState, MultiBufferSource buffer, int packedLight, float partialTicks) {
        if (renderState instanceof PlayerRenderState playerRenderState) {
            GlovesModel glovesModel = this.glovesModel;

            GlovesItem glovesItem = (GlovesItem) stack.getItem();

            glovesModel.setAllVisible(false);

            ModelPart gloveArm = arm == HumanoidArm.RIGHT ? glovesModel.rightArm : glovesModel.leftArm;
            boolean flag = arm != HumanoidArm.LEFT;
            float f = flag ? 1.0F : -1.0F;

            gloveArm.resetPose();
            gloveArm.visible = true;
            gloveArm.zRot = f * 0.1F;

            boolean isSlim = playerRenderState.skin.model() == PlayerSkin.Model.SLIM;
            float offset = isSlim ? 0.0425F : 0.0F;
            poseStack.translate((f * offset) - 0.0025, 0.0025, -0.0025);

            gloveArm.render(poseStack, ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(glovesItem.getGlovesTexture()), stack.hasFoil()), packedLight, OverlayTexture.NO_OVERLAY, -1);

            if (stack.is(ItemTags.DYEABLE)) {
                IClientItemExtensions extensions = IClientItemExtensions.of(stack);
                int i = extensions.getDefaultDyeColor(stack);
                int color = ARGB.opaque(i);
                ResourceLocation dyedTexture = ResourceLocation.parse(glovesItem.getGlovesTexture().toString().replace(".png", "_dyed.png"));
                VertexConsumer dyedConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(dyedTexture), stack.hasFoil());
                glovesModel.renderToBuffer(poseStack, dyedConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
            }

            ArmorStyle style = stack.get(AetherIIDataComponents.ARMOR_STYLE);
            if (style != null && Minecraft.getInstance().level != null) {
                TextureAtlasSprite sprite = this.armorStyleSpriteLookup.apply(new ArmorStyle.SpriteKey(Minecraft.getInstance().level.registryAccess(), style, "humanoid_gloves"));
                VertexConsumer consumer = sprite.wrap(buffer.getBuffer(RenderType.armorCutoutNoCull(AetherIIAtlases.ARMOR_STYLES_SHEET)));
                glovesModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}