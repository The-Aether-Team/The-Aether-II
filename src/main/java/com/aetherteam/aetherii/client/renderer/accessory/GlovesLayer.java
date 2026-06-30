package com.aetherteam.aetherii.client.renderer.accessory;

import com.aetherteam.aetherii.client.AetherIIDyeableClientItemExtensions;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.util.ARGB;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GlovesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> implements FirstPersonRendering {
    private final GlovesModel glovesModel;
    private final GlovesModel glovesModelSlim;
    private final GlovesModel glovesModelFirstPerson;
    private final GlovesModel glovesModelSlimFirstPerson;

    public GlovesLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.glovesModel = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES));
        this.glovesModelSlim = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_SLIM));
        this.glovesModelFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_FIRST_PERSON));
        this.glovesModelSlimFirstPerson = new GlovesModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.GLOVES_SLIM_FIRST_PERSON));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        ItemStack stack = AccessoryUtil.getFirst(entity, AccessoryContainer.SlotType.HANDWEAR).orElse(ItemStack.EMPTY);
        if (stack.isEmpty()) {
            return;
        }

        GlovesModel model = this.getModelForParent();
        this.copyParentModel(model);
        this.setGloveVisibility(model);
        this.renderGlovesModel(model, stack, poseStack, buffer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F);

        int color = AetherIIDyeableClientItemExtensions.getDefaultDyeColor(stack, 0);
        if (color != 0) {
            this.renderGlovesModel(model, stack, poseStack, buffer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), ARGB.redFloat(color), ARGB.greenFloat(color), ARGB.blueFloat(color));
        }
    }

    private GlovesModel getModelForParent() {
        if (this.getParentModel() instanceof PlayerModel<?> playerModel && ((PlayerModelAccessor) playerModel).aether$getSlim()) {
            return this.glovesModelSlim;
        }
        return this.glovesModel;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void copyParentModel(GlovesModel model) {
        if (this.getParentModel() instanceof HumanoidModel humanoidModel) {
            humanoidModel.copyPropertiesTo(model);
        }
    }

    private void setGloveVisibility(GlovesModel model) {
        model.setAllVisible(false);
        model.leftArm.visible = true;
        model.rightArm.visible = true;
    }

    private void renderGlovesModel(GlovesModel model, ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int overlay, float red, float green, float blue) {
        ResourceLocation texture = this.getGloveTexture(stack);
        VertexConsumer consumer = buffer.getBuffer(RenderType.armorCutoutNoCull(texture));
        model.renderToBuffer(poseStack, consumer, packedLight, overlay, red, green, blue, 1.0F);
    }

    private ResourceLocation getGloveTexture(ItemStack stack) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return new ResourceLocation(id.getNamespace(), "textures/entity/equipment/humanoid_gloves/" + id.getPath() + ".png");
    }

    @Override
    public void renderOnFirstPerson(HumanoidArm arm, ItemStack stack, Player player, PoseStack poseStack, PlayerModel<?> model, MultiBufferSource buffer, int packedLight) {
        GlovesModel gloves = ((PlayerModelAccessor) model).aether$getSlim() ? this.glovesModelSlimFirstPerson : this.glovesModelFirstPerson;
        ModelPart gloveArm = arm == HumanoidArm.RIGHT ? gloves.rightArm : gloves.leftArm;
        ModelPart playerArm = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        gloveArm.copyFrom(playerArm);

        this.renderGloveArm(gloveArm, stack, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F);
        int color = AetherIIDyeableClientItemExtensions.getDefaultDyeColor(stack, 0);
        if (color != 0) {
            this.renderGloveArm(gloveArm, stack, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, ARGB.redFloat(color), ARGB.greenFloat(color), ARGB.blueFloat(color));
        }
    }

    private void renderGloveArm(ModelPart arm, ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int overlay, float red, float green, float blue) {
        VertexConsumer consumer = buffer.getBuffer(RenderType.armorCutoutNoCull(this.getGloveTexture(stack)));
        arm.render(poseStack, consumer, packedLight, overlay, red, green, blue, 1.0F);
    }
}
