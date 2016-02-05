package com.gildedgames.aether.client.renderer.entities.projectiles;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import com.gildedgames.util.core.client.SpriteGeneric;

@SideOnly(Side.CLIENT)
public class RenderDaggerfrostSnowball extends Render<EntityDaggerfrostSnowball>
{
	
	public static final ResourceLocation TEXTURE = new ResourceLocation("aether", "textures/entities/projectiles/daggerfrost_snowball.png");

	public static final SpriteGeneric SPRITE = new SpriteGeneric("daggerfrost_snowball.png", 16, 16);
	
    public RenderDaggerfrostSnowball(RenderManager renderManager)
	{
		super(renderManager);
		
    	SPRITE.initSprite(16, 16, 0, 0, false);
    }

    public void doRender(EntityDaggerfrostSnowball entity, double posX, double posY, double posZ, float entityYaw, float partialTicks)
    {
    	GlStateManager.pushMatrix();
        this.bindEntityTexture(entity);
        GlStateManager.translate((float)posX, (float)posY, (float)posZ);
        GlStateManager.enableRescaleNormal();
        
        final float scale = 0.5F;
        
        GlStateManager.scale(scale, scale, scale);
        
        this.bindEntityTexture(entity);

        Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer renderer = tessellator.getWorldRenderer();
        
        this.renderEntity(renderer, SPRITE);
        
        tessellator.draw();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        
        super.doRender(entity, posX, posY, posZ, entityYaw, partialTicks);
    }

    private void renderEntity(WorldRenderer renderer, TextureAtlasSprite icon)
    {
    	float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        
        renderer.pos(-0.5D, -0.25D, 0.0D).tex((double)f, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
        renderer.pos(0.5D, -0.25D, 0.0D).tex((double)f1, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
        renderer.pos(0.5D, 0.75D, 0.0D).tex((double)f1, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
        renderer.pos(-0.5D, 0.75D, 0.0D).tex((double)f, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityDaggerfrostSnowball entity)
	{
		return TEXTURE;
	}
    
}