package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.client.AetherIIAtlases;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ArmorStyle;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Util;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Vector3f;

import java.util.function.Function;
import java.util.function.Predicate;

public class GlovesLayer<S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends RenderLayer<S, M> implements FirstPersonRendering {
    private static final Function<ArmorStyle.SpriteKey, TextureAtlasSprite> ARMOR_STYLE_SPRITE_LOOKUP = Util.memoize((key) -> Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AetherIIAtlases.ARMOR_STYLES_ID).getSprite(key.textureId()));
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

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S s, float v, float v1) {
        if (Minecraft.getInstance().player != null && s instanceof HumanoidRenderState humanoidRenderState) {
            AccessoryUtil.getFirst(Minecraft.getInstance().player, AccessoryContainer.SlotType.HANDWEAR).ifPresent((stack) -> {
                Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
                Identifier texture = Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/entity/equipment/humanoid_gloves/" + id.getPath() + ".png");
                GlovesModel glovesModel = this.glovesModel;

                if (this.getParentModel() instanceof HumanoidModel<?> humanoidModel) {
                    if (humanoidModel instanceof PlayerModel playerModel) {
                        PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
                        glovesModel = playerModelAccessor.aether$getSlim() ? this.glovesModelSlim : this.glovesModel;
                    }
                    copyPropertiesTo(glovesModel, humanoidModel);
                }
                glovesModel.head.visible = false;
                glovesModel.hat.visible = false;
                glovesModel.body.visible = false;
                glovesModel.leftLeg.visible = false;
                glovesModel.rightLeg.visible = false;

                submitNodeCollector
                        .submitModel(
                                glovesModel,
                                humanoidRenderState,
                                poseStack,
                                RenderTypes.armorCutoutNoCull(texture),
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                -1,
                                null,
                                s.outlineColor,
                                null
                        );

                if (stack.has(DataComponents.DYED_COLOR)) {
                    IClientItemExtensions extensions = IClientItemExtensions.of(stack);
                    int color = ARGB.opaque(extensions.getDefaultDyeColor(stack));

                    submitNodeCollector
                            .submitModel(
                                    glovesModel,
                                    humanoidRenderState,
                                    poseStack,
                                    RenderTypes.armorCutoutNoCull(texture),
                                    packedLight,
                                    OverlayTexture.NO_OVERLAY,
                                    color,
                                    null,
                                    s.outlineColor,
                                    null
                            );

                }

                ArmorStyle style = stack.get(AetherIIDataComponents.ARMOR_STYLE);
                if (style != null && Minecraft.getInstance().level != null) {
                    TextureAtlasSprite sprite = ARMOR_STYLE_SPRITE_LOOKUP.apply(new ArmorStyle.SpriteKey(Minecraft.getInstance().level.registryAccess(), style, "humanoid_gloves"));
                    submitNodeCollector
                            .submitModel(
                                    glovesModel,
                                    humanoidRenderState,
                                    poseStack,
                                    RenderTypes.armorCutoutNoCull(AetherIIAtlases.ARMOR_STYLES_SHEET),
                                    packedLight,
                                    OverlayTexture.NO_OVERLAY,
                                    -1,
                                    sprite,
                                    s.outlineColor,
                                    null
                            );
                }
            });
        }
    }

    public void copyPropertiesTo(HumanoidModel<?> model, HumanoidModel<?> from) {
        copyFrom(model.head, from.head);
        copyFrom(model.body, from.body);
        copyFrom(model.rightArm, from.rightArm);
        copyFrom(model.leftArm, from.leftArm);
        copyFrom(model.rightLeg, from.rightLeg);
        copyFrom(model.leftLeg, from.leftLeg);
    }

    public void copyFrom(ModelPart model, ModelPart from) {
        model.offsetPos(new Vector3f(from.x, from.y, from.z));
        model.offsetRotation(new Vector3f(from.xRot, from.yRot, from.zRot));
    }

    @Override
    public void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, Player player, PoseStack poseStack, PlayerModel model, SubmitNodeCollector collector, int packedLight) {
        PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) model;
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        Identifier texture = Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/entity/equipment/humanoid_gloves/" + id.getPath() + ".png");
        GlovesModel model2 = playerModelAccessor.aether$getSlim() ? this.glovesModelSlimFirstPerson : this.glovesModelFirstPerson;

        ModelPart gloveArm = arm == HumanoidArm.RIGHT ? model2.rightArm : model2.leftArm;
        ModelPart playerArm = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        gloveArm.resetPose();
        gloveArm.offsetRotation(new Vector3f(playerArm.xRot, playerArm.yRot, playerArm.zRot));

        collector
                .submitModelPart(
                        gloveArm,
                        poseStack,
                        RenderTypes.armorCutoutNoCull(texture),
                        packedLight,
                        OverlayTexture.NO_OVERLAY,
                        null
                );

        if (stack.has(DataComponents.DYED_COLOR)) {
            IClientItemExtensions extensions = IClientItemExtensions.of(stack);
            int color = ARGB.opaque(extensions.getDefaultDyeColor(stack));
            collector
                    .submitModelPart(
                            gloveArm,
                            poseStack,
                            RenderTypes.armorCutoutNoCull(texture),
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            null,
                            color,
                            null
                    );
        }

        ArmorStyle style = stack.get(AetherIIDataComponents.ARMOR_STYLE);
        if (style != null && Minecraft.getInstance().level != null) {
            TextureAtlasSprite sprite = ARMOR_STYLE_SPRITE_LOOKUP.apply(new ArmorStyle.SpriteKey(Minecraft.getInstance().level.registryAccess(), style, "humanoid_gloves"));
            collector
                    .submitModelPart(
                            gloveArm,
                            poseStack,
                            RenderTypes.armorCutoutNoCull(AetherIIAtlases.ARMOR_STYLES_SHEET),
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            sprite
                    );
        }
    }
}