package com.aetherteam.aetherii.client.renderer.accessory;

import java.util.function.Function;

import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ArmorStyle;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class GlovesLayer<S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends RenderLayer<S, M> implements FirstPersonRendering {
    private static final Function<ArmorStyle.SpriteKey, TextureAtlasSprite> ARMOR_STYLE_SPRITE_LOOKUP = Util.memoize((key) -> Minecraft.getInstance().getModelManager().getAtlas(AetherIIAtlases.ARMOR_STYLES_SHEET).getSprite(key.textureId()));
    private final GlovesModel glovesModel;
    private final GlovesModel glovesModelSlim;
    private final GlovesModel glovesModelFirstPerson;
    private final GlovesModel glovesModelSlimFirstPerson;

    public GlovesLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
        this.glovesModel = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES));
        this.glovesModelSlim = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_SLIM));
        this.glovesModelFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_FIRST_PERSON));
        this.glovesModelSlimFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_SLIM_FIRST_PERSON));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, S state, float netHeadYaw, float headPitch) {
        if (Minecraft.getInstance().player != null) {
            AccessoryUtil.getFirst(Minecraft.getInstance().player, AccessoryContainer.SlotType.HANDWEAR).ifPresent((stack) -> {
                 id = BuiltInRegistries.ITEM.getKey(stack.getItem());
                 texture = Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/entity/equipment/humanoid_gloves/" + id.getPath() + ".png");
                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
                GlovesModel glovesModel = this.glovesModel;

                if (this.getParentModel() instanceof HumanoidModel humanoidModel) {
                    if (humanoidModel instanceof PlayerModel playerModel) {
                        PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
                        glovesModel = playerModelAccessor.aether$getSlim() ? this.glovesModelSlim : this.glovesModel;
                    }
                    humanoidModel.copyPropertiesTo(glovesModel);
                }
                glovesModel.setAllVisible(false);
                glovesModel.leftArm.visible = true;
                glovesModel.rightArm.visible = true;

                glovesModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

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
            });
        }
    }

    @Override
    public void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, Player player, PoseStack poseStack, PlayerModel playerModel, MultiBufferSource buffer, int packedLight) {
        PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
         id = BuiltInRegistries.ITEM.getKey(stack.getItem());
         texture = Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/entity/equipment/humanoid_gloves/" + id.getPath() + ".png");
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
        GlovesModel model = playerModelAccessor.aether$getSlim() ? this.glovesModelSlimFirstPerson : this.glovesModelFirstPerson;

        ModelPart gloveArm = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        ModelPart playerArm = arm == HumanoidArm.RIGHT ? playerModel.rightArm : playerModel.leftArm;
        gloveArm.resetPose();
        gloveArm.copyFrom(playerArm);
        gloveArm.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);

        if (stack.is(ItemTags.DYEABLE)) {
            IClientItemExtensions extensions = IClientItemExtensions.of(stack);
            int color = ARGB.opaque(extensions.getDefaultDyeColor(stack));
            VertexConsumer dyedConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
            gloveArm.render(poseStack, dyedConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
        }

        ArmorStyle style = stack.get(AetherIIDataComponents.ARMOR_STYLE);
        if (style != null && Minecraft.getInstance().level != null) {
            TextureAtlasSprite sprite = ARMOR_STYLE_SPRITE_LOOKUP.apply(new ArmorStyle.SpriteKey(Minecraft.getInstance().level.registryAccess(), style, "humanoid_gloves"));
            VertexConsumer styleConsumer = sprite.wrap(buffer.getBuffer(RenderType.armorCutoutNoCull(AetherIIAtlases.ARMOR_STYLES_SHEET)));
            gloveArm.render(poseStack, styleConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
