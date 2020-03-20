package com.gildedgames.aether.client.renderer.tiles;

import com.gildedgames.aether.client.models.entities.tile.ModelMoaEgg;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.tiles.TileEntityMoaEgg;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityMoaEggRenderer extends TileEntitySpecialRenderer<TileEntityMoaEgg>
{

	private static final ResourceLocation TEXTURE_BASE = AetherCore.getResource("textures/tile_entities/moa_egg/base.png");

	private static final ResourceLocation TEXTURE_BEAK = AetherCore.getResource("textures/tile_entities/moa_egg/beak.png");

	public final ModelMoaEgg model = new ModelMoaEgg();

	@Override
	public void render(final TileEntityMoaEgg egg, final double x, final double y, final double z, final float partialTicks, final int destroyStage,
			final float alpha)
	{
		final MoaGenePool genePool = egg.getGenePool();

		if (genePool == null || genePool.getFeathers() == null)
		{
			return;
		}

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180f, 1f, 0f, 1f);

		this.renderColor(genePool.getFeathers().gene().data().getRGB());

		this.bindTexture(TEXTURE_BASE);

		this.model.renderAll(0.0625F);

		this.renderColor(genePool.getKeratin().gene().data().getRGB());

		this.bindTexture(TEXTURE_BEAK);

		this.model.renderAll(0.0625F);

		this.renderColor(genePool.getFeathers().gene().data().darker().getRGB());

		GL11.glPopMatrix();

		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}

	public void renderColor(final int color)
	{
		final float red = ((color >> 16) & 0xff) / 255F;
		final float green = ((color >> 8) & 0xff) / 255F;
		final float blue = (color & 0xff) / 255F;

		GL11.glColor3f(red, green, blue);
	}

}
