package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.tiles.TileEntitySkyrootSign;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TileEntitySkyrootSignRenderer extends TileEntitySpecialRenderer<TileEntitySkyrootSign>
{
	private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("aether:textures/tile_entities/skyroot_sign.png");

	/** The ModelSign instances for use in this renderer */
	private final ModelSign model = new ModelSign();

	@Override
	public void renderTileEntityAt(TileEntitySkyrootSign te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		Block block = te.getBlockType();

		GlStateManager.pushMatrix();

		float modelScale = 0.6666667F;

		if (block == BlocksAether.standing_skyroot_sign)
		{
			float direction = (te.getBlockMetadata() * 360) / 16.0F;

			GlStateManager.translate(x + 0.5F, y + 0.75F * modelScale, z + 0.5F);
			GlStateManager.rotate(-direction, 0.0F, 1.0F, 0.0F);

			this.model.signStick.showModel = true;
		}
		else
		{
			int direction = te.getBlockMetadata();

			float angle = 0.0F;

			if (direction == 2)
			{
				angle = 180.0F;
			}

			if (direction == 4)
			{
				angle = 90.0F;
			}

			if (direction == 5)
			{
				angle = -90.0F;
			}

			GlStateManager.translate(x + 0.5F, y + 0.75F * modelScale, z + 0.5F);
			GlStateManager.rotate(-angle, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -0.3125F, -0.4375F);

			this.model.signStick.showModel = false;
		}

		if (destroyStage >= 0)
		{
			this.bindTexture(DESTROY_STAGES[destroyStage]);

			GlStateManager.matrixMode(GL11.GL_TEXTURE);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 2.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		}
		else
		{
			this.bindTexture(SIGN_TEXTURE);
		}

		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		GlStateManager.scale(modelScale, -modelScale, -modelScale);

		this.model.renderSign();

		GlStateManager.popMatrix();

		FontRenderer fontrenderer = this.getFontRenderer();

		float textScale = 0.015625F * modelScale;
		GlStateManager.translate(0.0F, 0.5F * modelScale, 0.07F * modelScale);
		GlStateManager.scale(textScale, -textScale, textScale);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * textScale);
		GlStateManager.depthMask(false);
		int fontColor = 0xFFFFFFFF;

		if (destroyStage < 0)
		{
			GlStateManager.disableLighting();

			for (int i = 0; i < te.signText.length; ++i)
			{
				if (te.signText[i] != null)
				{
					ITextComponent text = te.signText[i];

					List<ITextComponent> list = GuiUtilRenderComponents.splitText(text, 90, fontrenderer, false, true);

					String line = list != null && list.size() > 0 ? list.get(0).getFormattedText() : "";

					if (i == te.lineBeingEdited)
					{
						line = "> " + line + " <";

						fontrenderer.drawString(line, -fontrenderer.getStringWidth(line) / 2, i * 10 - te.signText.length * 5, fontColor);
					}
					else
					{
						fontrenderer.drawString(line, -fontrenderer.getStringWidth(line) / 2, i * 10 - te.signText.length * 5, fontColor);
					}
				}
			}

			GlStateManager.enableLighting();
		}

		GlStateManager.depthMask(true);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();

		if (destroyStage >= 0)
		{
			GlStateManager.matrixMode(GL11.GL_TEXTURE);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		}
	}
}
