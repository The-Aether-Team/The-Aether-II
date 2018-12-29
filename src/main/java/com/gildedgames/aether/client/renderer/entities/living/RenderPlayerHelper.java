package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.util.helpers.EntityUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderSpecificHandEvent;

public class RenderPlayerHelper {
    public static void renderFirstPersonHand(RenderSpecificHandEvent event, PlayerAether player) {
        final ItemStack stack = player.getEquipmentModule().getInventory().getStackInSlot(2);

        final PatronRewardArmor armor = player.getPatronRewardsModule().getChoices().getArmorChoice();

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

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        final EnumHandSide hand = Minecraft.getMinecraft().gameSettings.mainHand;

        if (hand == EnumHandSide.LEFT) {
            GlStateManager.scale(-1.0f, 1.0f, 1.0f);
        }

        RenderPlayerHelper.renderArmFirstPerson(stack, equipProgress, swingProgress, EnumHandSide.RIGHT);

        GlStateManager.popMatrix();
    }

    private static void renderArmFirstPerson(final ItemStack stack, final float equipProgress, final float swingProgress, final EnumHandSide hand) {
        final boolean flag = hand != EnumHandSide.LEFT;
        final float f = flag ? 1.0F : -1.0F;
        final float f1 = MathHelper.sqrt(swingProgress);
        final float f2 = -0.3F * MathHelper.sin(f1 * (float) Math.PI);
        final float f3 = 0.4F * MathHelper.sin(f1 * ((float) Math.PI * 2F));
        final float f4 = -0.4F * MathHelper.sin(swingProgress * (float) Math.PI);

        GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + equipProgress * -0.6F, f4 + -0.71999997F);

        GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
        final float f5 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        final float f6 = MathHelper.sin(f1 * (float) Math.PI);
        GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
        final AbstractClientPlayer abstractclientplayer = Minecraft.getMinecraft().player;

        GlStateManager.scale(0.78F, 0.78F, 0.78F);

        GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);

        GlStateManager.translate(0.07F, -0.018F, -0.035F);

        String skinType = EntityUtil.getSkin(Minecraft.getMinecraft().player);

        GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);

        GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);

        GlStateManager.translate(-0.03F, 0.04F, -0.04F);

        if (!skinType.equals("slim")) {
            GlStateManager.translate(-0.03F, 0.0F, -0.02F);
        } else {
            GlStateManager.translate(0.0F, -0.07F, 0.0F);
        }

        GlStateManager.disableCull();

        if (flag) {
            final RenderLivingBase<?> playerRender = Minecraft.getMinecraft().getRenderManager().getSkinMap().get(skinType);

            final ModelBiped t = !skinType.equals("slim") ? new ModelBiped(0.5F) : new ModelPlayer(1F, true);
            t.bipedBody.showModel = true;
            t.bipedRightLeg.showModel = true;
            t.bipedLeftLeg.showModel = true;

            t.setModelAttributes(playerRender.getMainModel());
            //t.setLivingAnimations(entity, p_177182_2_, p_177182_3_, partialTicks);
            GlStateManager.enableBlend();
            t.swingProgress = 0.0F;
            t.isSneak = false;

            t.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, abstractclientplayer);
            t.bipedRightArm.rotateAngleX = 0.0F;

            //GlStateManager.scale(0.75F, 0.75F, 0.75F);
            t.bipedRightArm.render(0.0625F);
            //t.bipedRightArmwear.rotateAngleX = 0.0F;
            //t.bipedRightArmwear.render(0.0625F);
            GlStateManager.disableBlend();

            GlStateManager.color(1.0F, 1.0F, 1.0F);
        }

        GlStateManager.enableCull();
    }
}
