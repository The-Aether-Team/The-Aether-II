package com.gildedgames.aether.client.renderer.tiles;

import com.gildedgames.aether.common.blocks.construction.signs.BlockStandingSkyrootSign;
import com.gildedgames.aether.common.blocks.construction.signs.BlockWallSkyrootSign;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootSign;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.SignModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class TileEntitySkyrootSignRenderer extends TileEntityRenderer<TileEntitySkyrootSign>
{
	private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("aether:textures/tile_entities/skyroot_sign.png");

	/** The ModelSign instances for use in this renderer */
	private final SignModel model = new SignModel();

	@Override
	public void render(final TileEntitySkyrootSign te, final double x, final double y, final double z, final float partialTicks, final int destroyStage)
	{
		BlockState state = te.getBlockState();
		GlStateManager.pushMatrix();
		float f = 0.6666667F;
		if (state.getBlock() instanceof BlockStandingSkyrootSign)
		{
			GlStateManager.translatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			GlStateManager.rotatef(-((float) (state.get(BlockStandingSkyrootSign.ROTATION) * 360) / 16.0F), 0.0F, 1.0F, 0.0F);
			this.model.getSignStick().showModel = true;
		}
		else
		{
			GlStateManager.translatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			GlStateManager.rotatef(-state.get(BlockWallSkyrootSign.FACING).getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translatef(0.0F, -0.3125F, -0.4375F);
			this.model.getSignStick().showModel = false;
		}

		if (destroyStage >= 0)
		{
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scalef(4.0F, 2.0F, 1.0F);
			GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		}
		else
		{
			this.bindTexture(SIGN_TEXTURE);
		}

		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		GlStateManager.scalef(0.6666667F, -0.6666667F, -0.6666667F);
		this.model.renderSign();
		GlStateManager.popMatrix();
		FontRenderer font = this.getFontRenderer();
		float f1 = 0.010416667F;
		GlStateManager.translatef(0.0F, 0.33333334F, 0.046666667F);
		GlStateManager.scalef(0.010416667F, -0.010416667F, 0.010416667F);
		GlStateManager.normal3f(0.0F, 0.0F, -0.010416667F);
		GlStateManager.depthMask(false);
		int i = te.func_214066_f().func_218388_g();
		if (destroyStage < 0)
		{
			for (int lineIndex = 0; lineIndex < 4; ++lineIndex)
			{
				String string = te.getRenderText(lineIndex, (line) -> {
					List<ITextComponent> components = RenderComponentsUtil.splitText(line, 90, font, false, true);
					return components.isEmpty() ? "" : components.get(0).getFormattedText();
				});

				if (string != null)
				{
					int i3 = lineIndex * 10 - te.signText.length * 5;
					font.drawString(string, (float) (-font.getStringWidth(string) / 2), (float) (i3), i);
					if (lineIndex == te.func_214064_s() && te.func_214065_t() >= 0)
					{
						int width = font.getStringWidth(string.substring(0, Math.max(Math.min(te.func_214065_t(), string.length()), 0)));
						int l = font.getBidiFlag() ? -1 : 1;
						int i1 = (width - font.getStringWidth(string) / 2) * l;
						if (te.func_214069_r())
						{
							if (te.func_214065_t() < string.length())
							{
								AbstractGui.fill(i1, i3 - 1, i1 + 1, i3 + 9, -16777216 | i);
							}
							else
							{
								font.drawString("_", (float) i1, (float) i3, i);
							}
						}

						if (te.func_214067_u() != te.func_214065_t())
						{
							int k1 = Math.min(te.func_214065_t(), te.func_214067_u());
							int l1 = Math.max(te.func_214065_t(), te.func_214067_u());
							int i2 = (font.getStringWidth(string.substring(0, k1)) - font.getStringWidth(string) / 2) * l;
							int j2 = (font.getStringWidth(string.substring(0, l1)) - font.getStringWidth(string) / 2) * l;
							this.func_217657_a(Math.min(i2, j2), i3, Math.max(i2, j2), i3 + 9);
						}
					}
				}
			}
		}

		GlStateManager.depthMask(true);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
		if (destroyStage >= 0)
		{
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}

	private void func_217657_a(int p_217657_1_, int p_217657_2_, int p_217657_3_, int p_217657_4_) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.color4f(0.0F, 0.0F, 255.0F, 255.0F);
		GlStateManager.disableTexture();
		GlStateManager.enableColorLogicOp();
		GlStateManager.logicOp(GlStateManager.LogicOp.OR_REVERSE);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos((double)p_217657_1_, (double)p_217657_4_, 0.0D).endVertex();
		bufferbuilder.pos((double)p_217657_3_, (double)p_217657_4_, 0.0D).endVertex();
		bufferbuilder.pos((double)p_217657_3_, (double)p_217657_2_, 0.0D).endVertex();
		bufferbuilder.pos((double)p_217657_1_, (double)p_217657_2_, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.disableColorLogicOp();
		GlStateManager.enableTexture();
	}
}
