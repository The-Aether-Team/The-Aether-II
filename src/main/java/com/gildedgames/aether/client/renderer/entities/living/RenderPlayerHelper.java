package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPatronRewardModule;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderSpecificHandEvent;

public class RenderPlayerHelper {
    public static void renderFirstPersonHand(RenderSpecificHandEvent event, PlayerAether player) {
        final ItemStack stack = player.getModule(PlayerEquipmentModule.class).getInventory().getStackInSlot(2);

        final PatronRewardArmor armor = player.getModule(PlayerPatronRewardModule.class).getChoices().getArmorChoice();

        if (armor != null && armor.getArmorGloveTexture(EntityUtil.getSkin(player.getEntity()).equals("slim")) != null) {
            RenderPlayerHelper.renderGloves(armor.getArmorGloveTexture(EntityUtil.getSkin(player.getEntity()).equals("slim")), stack, event.getPartialTicks(),
                    event.getInterpolatedPitch(), event.getSwingProgress(), event.getEquipProgress());
        }

        if (stack.getItem() instanceof ItemAetherGloves) {
            RenderPlayerHelper.renderGloves(((ItemAetherGloves) stack.getItem()).getGloveTexture(player.getEntity()), stack, event.getPartialTicks(),
                    event.getInterpolatedPitch(), event.getSwingProgress(), event.getEquipProgress());
        }
    }

    private static void renderGloves(ResourceLocation texture, ItemStack stack, float partialTicks, float pitch, float swingProgress, float equipProgress) {
        GlStateManager.pushMatrix();

        Minecraft.getInstance().getTextureManager().bindTexture(texture);

        final HandSide hand = Minecraft.getInstance().gameSettings.mainHand;

        if (hand == HandSide.LEFT) {
            GlStateManager.scalef(-1.0f, 1.0f, 1.0f);
        }

        RenderPlayerHelper.renderArmFirstPerson(stack, equipProgress, swingProgress, HandSide.RIGHT);

        GlStateManager.popMatrix();
    }

    private static void renderArmFirstPerson(final ItemStack stack, final float equipProgress, final float swingProgress, final HandSide hand) {
        final boolean flag = hand != HandSide.LEFT;
        final float f = flag ? 1.0F : -1.0F;
        final float f1 = MathHelper.sqrt(swingProgress);
        final float f2 = -0.3F * MathHelper.sin(f1 * (float) Math.PI);
        final float f3 = 0.4F * MathHelper.sin(f1 * ((float) Math.PI * 2F));
        final float f4 = -0.4F * MathHelper.sin(swingProgress * (float) Math.PI);

        GlStateManager.translatef(f * (f2 + 0.64000005F), f3 + -0.6F + equipProgress * -0.6F, f4 + -0.71999997F);

        GlStateManager.rotatef(f * 45.0F, 0.0F, 1.0F, 0.0F);
        final float f5 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        final float f6 = MathHelper.sin(f1 * (float) Math.PI);
        GlStateManager.rotatef(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
        final AbstractClientPlayerEntity abstractclientplayer = Minecraft.getInstance().player;

        GlStateManager.scalef(0.78F, 0.78F, 0.78F);

        GlStateManager.translatef(f * -1.0F, 3.6F, 3.5F);

        GlStateManager.translatef(0.07F, -0.018F, -0.035F);

        String skinType = EntityUtil.getSkin(Minecraft.getInstance().player);

        GlStateManager.rotatef(f * 120.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotatef(200.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(f * -135.0F, 0.0F, 1.0F, 0.0F);

        GlStateManager.translatef(f * 5.6F, 0.0F, 0.0F);

        GlStateManager.translatef(-0.03F, 0.04F, -0.04F);

        if (!skinType.equals("slim")) {
            GlStateManager.translatef(-0.03F, 0.0F, -0.02F);
        } else {
            GlStateManager.translatef(0.0F, -0.07F, 0.0F);
        }

        GlStateManager.disableCull();

        if (flag) {
            final PlayerRenderer playerRender = Minecraft.getInstance().getRenderManager().getSkinMap().get(skinType);

            final PlayerModel<AbstractClientPlayerEntity> t = !skinType.equals("slim") ? new PlayerModel<>(0.5F, false) : new PlayerModel<>(1F, true);
            t.field_78115_e.showModel = true; // mainBody
            t.bipedRightLeg.showModel = true;
            t.bipedLeftLeg.showModel = true;

            t.setModelAttributes(playerRender.getEntityModel());
            //t.setLivingAnimations(entity, p_177182_2_, p_177182_3_, partialTicks);
            GlStateManager.enableBlend();
            t.swingProgress = 0.0F;
            t.isSneak = false;

            t.setRotationAngles(abstractclientplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            t.bipedRightArm.rotateAngleX = 0.0F;

            //GlStateManager.scalef(0.75F, 0.75F, 0.75F);
            t.bipedRightArm.render(0.0625F);
            //t.bipedRightArmwear.rotateAngleX = 0.0F;
            //t.bipedRightArmwear.render(0.0625F);
            GlStateManager.disableBlend();

            GlStateManager.color3f(1.0F, 1.0F, 1.0F);
        }

        GlStateManager.enableCull();
    }
}
