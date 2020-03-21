package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPatronRewardModule;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderPlayerHelper
{
    public static void renderItemFirstPerson(AbstractClientPlayer player, float partialTicks, float pitch, EnumHand hand, float swingProgress, ItemStack stack, float equipProgress)
    {
        boolean flag = hand == EnumHand.MAIN_HAND;

        EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();

        GlStateManager.pushMatrix();

        if (stack.isEmpty())
        {
            if (flag)
            {
                renderGloveFirstPerson(player, equipProgress, swingProgress, enumhandside);
            }
        }
        else if (stack.getItem() instanceof ItemMap)
        {
            if (flag && player.getHeldItemOffhand().isEmpty())
            {
                renderMapFirstPerson(player, pitch, equipProgress, swingProgress);
            }
            else
            {
                renderMapFirstPersonSide(player, equipProgress, enumhandside, swingProgress);
            }
        }

        GlStateManager.popMatrix();
    }

    private static void renderGloves(AbstractClientPlayer player)
    {
        PlayerAether playerAether = PlayerAether.getPlayer(player);
        ItemStack gloveStack = playerAether.getModule(PlayerEquipmentModule.class).getInventory().getStackInSlot(2);

        if (!gloveStack.isEmpty() && gloveStack.getItem() instanceof ItemAetherGloves)
        {
            GlStateManager.disableCull();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            renderArm(playerAether, EnumHandSide.RIGHT, (ItemAetherGloves) gloveStack.getItem());
            renderArm(playerAether, EnumHandSide.LEFT, (ItemAetherGloves) gloveStack.getItem());
            GlStateManager.popMatrix();
            GlStateManager.enableCull();
        }
    }

    private static void renderArm(PlayerAether playerAether, EnumHandSide hand, ItemAetherGloves gloves)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(playerAether, gloves));

        GlStateManager.pushMatrix();

        float f = hand == EnumHandSide.RIGHT ? 1.0F : -1.0F;

        GlStateManager.scale(0.78F, 0.78F, 0.78F);

        GlStateManager.translate(0.185F, 0.0F, f * -0.065);

        GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f * -41.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(f * 0.3F, -1.1F, 0.45F);

        if (EntityUtil.getSkin(playerAether.getEntity()).equals("slim"))
        {
            GlStateManager.translate(0.0F, 0.0F, 0.0F);
        }
        else
        {
            GlStateManager.translate(0.0F, 0.03F, 0.0F);
        }

        if (hand == EnumHandSide.RIGHT)
        {
            GlStateManager.translate(0.00F, -0.3F, 0.0F);
            renderRightGlove(playerAether, gloves);
        }
        else
        {
            GlStateManager.translate(0.01F, -0.3F, 0.0F);
            renderLeftArmGlove(playerAether, gloves);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private static void renderMapFirstPerson(AbstractClientPlayer player, float pitch, float equipProgress, float swingProgress)
    {
        GlStateManager.pushMatrix();
        float f = MathHelper.sqrt(swingProgress);
        float f1 = -0.2F * MathHelper.sin(swingProgress * (float)Math.PI);
        float f2 = -0.4F * MathHelper.sin(f * (float)Math.PI);
        GlStateManager.translate(0.0F, -f1 / 2.0F, f2);
        float f3 = getMapAngleFromPitch(pitch);
        GlStateManager.translate(0.0F, 0.04F + equipProgress * -1.2F + f3 * -0.5F, -0.72F);
        GlStateManager.rotate(f3 * -85.0F, 1.0F, 0.0F, 0.0F);
        renderGloves(player);
        GlStateManager.popMatrix();
    }

    private static void renderMapFirstPersonSide(AbstractClientPlayer player, float equipProgress, EnumHandSide enumhandside, float swingProgress)
    {
        float f = enumhandside == EnumHandSide.RIGHT ? 1.0F : -1.0F;

        GlStateManager.pushMatrix();
        GlStateManager.translate(f * 0.125F, -0.125F, 0.0F);
        GlStateManager.rotate(f * 10.0F, 0.0F, 0.0F, 1.0F);
        renderGloveFirstPerson(player, equipProgress, swingProgress, enumhandside);
        GlStateManager.popMatrix();
    }

    private static void renderGloveFirstPerson(AbstractClientPlayer player, float equipProgress, float swingProgress, EnumHandSide enumhandside)
    {
        PlayerAether playerAether = PlayerAether.getPlayer(player);
        ItemStack gloveStack = playerAether.getModule(PlayerEquipmentModule.class).getInventory().getStackInSlot(2);

        if (!gloveStack.isEmpty() && gloveStack.getItem() instanceof ItemAetherGloves)
        {
            boolean flag = enumhandside != EnumHandSide.LEFT;
            float f = flag ? 1.0F : -1.0F;
            float f1 = MathHelper.sqrt(swingProgress);
            float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
            float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
            float f4 = -0.4F * MathHelper.sin(swingProgress * (float)Math.PI);
            GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + equipProgress * -0.6F, f4 + -0.71999997F);
            GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
            float f5 = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
            float f6 = MathHelper.sin(f1 * (float)Math.PI);
            GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);

            GlStateManager.scale(0.78F, 0.78F, 0.78F);

            GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);

            GlStateManager.translate(f * 0.03F, 0.0F, -0.03F);

            GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);

            if (EntityUtil.getSkin(player).equals("slim"))
            {
                GlStateManager.translate(0.0F, 0.0F, 0.0F);
            }
            else
            {
                GlStateManager.translate(0.0F, 0.03F, 0.0F);
            }

            GlStateManager.disableCull();

            if (flag)
            {
                renderRightGlove(playerAether, (ItemAetherGloves) gloveStack.getItem());
            }
            else
            {
                renderLeftArmGlove(playerAether, (ItemAetherGloves) gloveStack.getItem());
            }

            GlStateManager.enableCull();
        }
    }

    private static void renderRightGlove(PlayerAether playerAether, ItemAetherGloves gloves)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(playerAether, gloves));

        ModelBiped model = !EntityUtil.getSkin(playerAether.getEntity()).equals("slim") ? new ModelPlayer(1.0F, false) : new ModelPlayer(1.0F, true);

        GlStateManager.enableBlend();
        model.swingProgress = 0.0F;
        model.isSneak = false;
        model.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, playerAether.getEntity());
        model.bipedRightArm.rotateAngleX = 0.0F;
        model.bipedRightArm.render(0.0625F);
        GlStateManager.disableBlend();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static void renderLeftArmGlove(PlayerAether playerAether, ItemAetherGloves gloves)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(playerAether, gloves));

        ModelBiped model = !EntityUtil.getSkin(playerAether.getEntity()).equals("slim") ? new ModelPlayer(1.0F, false) : new ModelPlayer(1.0F, true);

        GlStateManager.enableBlend();
        model.isSneak = false;
        model.swingProgress = 0.0F;
        model.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, playerAether.getEntity());
        model.bipedLeftArm.rotateAngleX = 0.0F;
        model.bipedLeftArm.render(0.0625F);
        GlStateManager.disableBlend();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static ResourceLocation getTexture(PlayerAether playerAether, ItemAetherGloves gloves)
    {
        final PatronRewardArmor armor = playerAether.getModule(PlayerPatronRewardModule.class).getChoices().getArmorChoice();

        ResourceLocation texture = gloves.getGloveTexture(playerAether.getEntity());

        if (armor != null && armor.getArmorGloveTexture(EntityUtil.getSkin(playerAether.getEntity()).equals("slim")) != null)
        {
            texture = armor.getArmorGloveTexture(EntityUtil.getSkin(playerAether.getEntity()).equals("slim"));
        }

        return texture;
    }

    private static float getMapAngleFromPitch(float pitch)
    {
        float f = 1.0F - pitch / 45.0F + 0.1F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = -MathHelper.cos(f * (float)Math.PI) * 0.5F + 0.5F;

        return f;
    }
}
