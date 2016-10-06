package com.gildedgames.aether.common.items;

import com.gildedgames.aether.common.crafting.loot.IItemSelector;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Random;

public class ItemIrradiated extends Item
{

    private static final List<RenderInfo> renders = Lists.newArrayList();

    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    private IItemSelector itemSelector;

    public ItemIrradiated(IItemSelector itemSelector)
    {
        this.itemSelector = itemSelector;
    }

    public IItemSelector getItemSelector()
    {
        return this.itemSelector;
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

        RenderInfo renderInfo = null;

        for (RenderInfo render : renders)
        {
            if (render.getActualStack() == stack)
            {
                renderInfo = render;
                break;
            }
        }

        if (renderInfo == null)
        {
            renderInfo = new RenderInfo(stack);

            renderInfo.setLastTime(System.currentTimeMillis());

            renders.add(renderInfo);
        }

        long timeDif = System.currentTimeMillis() - renderInfo.getLastTime();

        if (renderInfo.getLastStackToRender() == null || timeDif >= 500L)
        {
            Random rand = new Random();

            while(true)
            {
                renderInfo.setLastStackToRender(this.getItemSelector().getRandomItem(rand));

                renderInfo.setLastModelToRender(itemRenderer.getItemModelMesher().getItemModel(renderInfo.getLastStackToRender()));

                if (!renderInfo.getLastModelToRender().isGui3d())
                {
                    break;
                }
            }

            renderInfo.setLastTime(System.currentTimeMillis());
        }

        Minecraft mc = Minecraft.getMinecraft();

        GlStateManager.pushMatrix();

        GlStateManager.disableLighting();

        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        mc.getRenderItem().renderModel(renderInfo.getLastModelToRender(), -0xE0E0E0, stack);

        this.renderEffect(renderInfo.getLastModelToRender(), stack);

        GlStateManager.enableLighting();

        GlStateManager.popMatrix();

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

    private class RenderInfo
    {

        private ItemStack actualStack, lastStackToRender;

        private IBakedModel lastModelToRender;

        private long lastTime;

        public RenderInfo(ItemStack actualStack)
        {
            this.actualStack = actualStack;
        }

        public long getLastTime()
        {
            return this.lastTime;
        }

        public void setLastTime(long lastTime)
        {
            this.lastTime = lastTime;
        }

        public ItemStack getActualStack()
        {
            return this.actualStack;
        }

        public ItemStack getLastStackToRender()
        {
            return lastStackToRender;
        }

        public IBakedModel getLastModelToRender()
        {
            return this.lastModelToRender;
        }

        public void setLastStackToRender(ItemStack stack)
        {
            this.lastStackToRender = stack;
        }

        public void setLastModelToRender(IBakedModel model)
        {
            this.lastModelToRender = model;
        }

    }

}