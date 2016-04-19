package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.tile.ModelLabyrinthTotem;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthTotem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityLabyrinthTotemRenderer extends TileEntitySpecialRenderer<TileEntityLabyrinthTotem>
{
	private static final ResourceLocation TEXTURE_TOTEM = AetherCore.getResource("textures/tile_entities/labyrinth_totem.png"),
				TEXTURE_TOTEM_GLOW = AetherCore.getResource("textures/tile_entities/labyrinth_totem_glow.png");

	private static final ModelLabyrinthTotem MODEL = new ModelLabyrinthTotem();

	@Override
	public void renderTileEntityAt(TileEntityLabyrinthTotem te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		float rotation = te.prevRenderTicks + (te.renderTicks - te.prevRenderTicks) * partialTicks;
		rotation /= 20;

		MODEL.Shape6.rotateAngleY = rotation;
		MODEL.Shape8.rotateAngleY = rotation;
		MODEL.Shape9.rotateAngleY = rotation;
		MODEL.Shape10.rotateAngleY = rotation;
		MODEL.Shape11.rotateAngleY = rotation;

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GlStateManager.rotate(180f, 1f, 0f, 1f);

		// Rendering as item... ?
		if (te == null)
		{
			GlStateManager.scale(0.5f, 0.5f, 0.5f);
		}

		this.bindTexture(TEXTURE_TOTEM);

		MODEL.renderAll(0.0625F);

		this.bindTexture(TEXTURE_TOTEM_GLOW);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

		GlStateManager.depthMask(true);

		char var5 = 61680;

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) var5 % 65536 / 1.0F, (float) var5 / 65536 / 1.0F);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.scale(1.01F, 1.01F, 1.01F);

		MODEL.renderAll(0.0625F);

		GlStateManager.disableBlend();

		GlStateManager.popMatrix();
	}
}
