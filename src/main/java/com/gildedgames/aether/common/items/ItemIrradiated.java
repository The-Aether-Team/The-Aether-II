package com.gildedgames.aether.common.items;

import com.gildedgames.aether.common.items.consumables.ItemContinuumOrbOld;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class ItemIrradiated extends Item
{

    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    private static ItemStack lastStackToRender;

    private static IBakedModel lastModelToRender;

    private static long lastTime = System.currentTimeMillis();

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        final ItemContinuumOrbOld.ContinuumItemSelector selector = new ItemContinuumOrbOld.ContinuumItemSelector();

        long timeDif = System.currentTimeMillis() - lastTime;
        if (lastStackToRender == null || timeDif >= 500L)
        {
            Random rand = new Random();

            while(true)
            {
                lastStackToRender = selector.getRandomItem(rand);

                lastModelToRender = itemRenderer.getItemModelMesher().getItemModel(lastStackToRender);

                if (!(lastStackToRender != null && lastStackToRender.getItem() instanceof ItemBlock) && !lastModelToRender.isGui3d())
                {
                    break;
                }
            }

            lastTime = System.currentTimeMillis();
        }

        Minecraft mc = Minecraft.getMinecraft();

        GlStateManager.disableLighting();

        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        GlStateManager.pushMatrix();

        mc.getRenderItem().renderModel(lastModelToRender, -0xE0E0E0, stack);

        GlStateManager.popMatrix();

        this.renderEffect(lastModelToRender, stack);

        return false;
    }

    private void renderEffect(IBakedModel model, ItemStack stack)
    {
        Minecraft mc = Minecraft.getMinecraft();

        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        mc.getTextureManager().bindTexture(RES_ITEM_GLINT);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0F, 8.0F, 8.0F);
        float f = (float)(Minecraft.getSystemTime() % 10000L) / 10000.0F / 8.0F;
        GlStateManager.translate(f, 0.0F, 0.0F);
        GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
        mc.getRenderItem().renderModel(model, -999999992);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0F, 8.0F, 8.0F);
        float f1 = (float)(Minecraft.getSystemTime() % 12873L) / 12873.0F / 8.0F;
        GlStateManager.translate(-f1, 0.0F, 0.0F);
        GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
        mc.getRenderItem().renderModel(model, -999999992);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }

}