package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.tile.ModelLabyrinthTotem;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthTotem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityLabyrinthTotemRenderer extends TileEntitySpecialRenderer<TileEntityLabyrinthTotem>
{
	private static final ResourceLocation TEXTURE_TOTEM = AetherCore.getResource("textures/tile_entities/labyrinth_totem.png"),
				TEXTURE_TOTEM_GLOW = AetherCore.getResource("textures/tile_entities/labyrinth_totem_glow.png");

	private final ModelLabyrinthTotem model = new ModelLabyrinthTotem();

	@Override
	public void renderTileEntityAt(TileEntityLabyrinthTotem te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		float rotation = 0;

		if (te != null)
		{
			rotation = te.prevRenderTicks + (te.renderTicks - te.prevRenderTicks) * partialTicks;
		}

		rotation /= 40;

		this.model.Shape6.rotateAngleY = rotation;
		this.model.Shape8.rotateAngleY = rotation;
		this.model.Shape9.rotateAngleY = rotation;
		this.model.Shape10.rotateAngleY = rotation;
		this.model.Shape11.rotateAngleY = rotation;

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.rotate(180f, 1f, 0f, 1f);

		// Rendering as item... ?
		if (te == null)
		{
			GlStateManager.scale(0.6f, 0.6f, 0.6f);
			GlStateManager.translate(0.0f, 1.2f, 0.0f);
		}

		this.bindTexture(TEXTURE_TOTEM);

		this.model.renderAll(0.0625F);

		this.bindTexture(TEXTURE_TOTEM_GLOW);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

		GlStateManager.depthMask(true);

		float var5 = 61680;

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 % 65536 / 1.0F, var5 / 65536 / 1.0F);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.scale(1.01F, 1.01F, 1.01F);

		this.model.renderAll(0.0625F);

		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		GlStateManager.disableBlend();

		GlStateManager.popMatrix();
	}
}
