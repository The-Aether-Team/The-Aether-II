package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.tile.ModelMoaEgg;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.tile_entities.TileEntityMoaEgg;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityMoaEggRenderer extends TileEntitySpecialRenderer<TileEntityMoaEgg>
{

	private static final ResourceLocation TEXTURE_BASE = new ResourceLocation(AetherCore.MOD_ID, "textures/tile_entities/moa_egg/base.png");
	
	private static final ResourceLocation TEXTURE_BEAK = new ResourceLocation(AetherCore.MOD_ID, "textures/tile_entities/moa_egg/beak.png");

	public ModelMoaEgg model = new ModelMoaEgg();

	@Override
	public void renderTileEntityAt(TileEntityMoaEgg egg, double x, double y, double z, float partialTicks, int destroyStage)
	{
		MoaGenePool genePool = MoaGenePool.get(egg);

		if (genePool == null || genePool.getFeathers() == null)
		{
			return;
		}

		/**
		 * TODO: Should not be constantly recreating resource locations.
		 */
		ResourceLocation BACK_MARKING = new ResourceLocation(AetherCore.MOD_ID, "textures/tile_entities/moa_egg/back/" + genePool.getMarks().gene().getBack().getResourcePath().replace("textures/entities/moa/back/", ""));
		ResourceLocation HEAD_MARKING = new ResourceLocation(AetherCore.MOD_ID, "textures/tile_entities/moa_egg/head/" + genePool.getMarks().gene().getHead().getResourcePath().replace("textures/entities/moa/head/", ""));

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180f, 1f, 0f, 1f);
		
		this.renderColor(genePool.getFeathers().gene().data().getRGB());

		this.bindTexture(TEXTURE_BASE);
		
		model.renderAll(0.0625F);
		
		this.renderColor(genePool.getKeratin().gene().data().getRGB());
		
		this.bindTexture(TEXTURE_BEAK);
		
		model.renderAll(0.0625F);
		
		this.renderColor(genePool.getFeathers().gene().data().darker().getRGB());
		
		this.bindTexture(HEAD_MARKING);
		
		model.renderAll(0.0625F);
		
		this.bindTexture(BACK_MARKING);
		
		model.renderAll(0.0625F);

		GL11.glPopMatrix();
	}
	
	public void renderColor(int color)
	{
		float red = ((color >> 16) & 0xff) / 255F;
		float green = ((color >> 8) & 0xff) / 255F;
		float blue = (color & 0xff) / 255F;
		
		GL11.glColor3f(red, green, blue);
	}

}
