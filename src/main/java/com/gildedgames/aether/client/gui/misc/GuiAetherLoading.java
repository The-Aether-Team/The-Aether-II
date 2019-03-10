package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.client.ClientEventHandler;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.gildedgames.orbis.lib.client.PartialTicks;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiViewer;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiContext;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.gildedgames.orbis.lib.util.InputHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.opengl.GL11;

public class GuiAetherLoading extends GuiViewer implements CustomLoadingRenderer.ICustomLoading
{
	private static final ResourceLocation HIGHLANDS = AetherCore.getResource("textures/gui/intro/highlands.png");

	private static final ResourceLocation HUE_BACKGROUND = AetherCore.getResource("textures/gui/intro/hue_background.png");

	public static float PERCENT = 0.0F;

	private GuiTexture highlands;

	private GuiText loading;

	private float lastPercent;

	public GuiAetherLoading()
	{
		super(new GuiElement(Dim2D.flush(), false));
	}

	@Override
	public void build(IGuiContext context)
	{
		this.getViewing().dim().mod().width(this.width).height(this.height).flush();

		final Pos2D center = InputHelper.getCenter();

		this.highlands = new GuiTexture(Dim2D.build().scale(0.45F).width(512).height(235).center(true).pos(center).flush(), HIGHLANDS);

		this.loading = new GuiText(Dim2D.build().center(true).pos(center).addY(70).flush(),
				new Text(new TextComponentTranslation("gui.aether.loading.indeterminate"), 1.0F));

		context.addChildren(this.highlands, this.loading);
	}

	@Override
	public void drawElements()
	{
		preventInnerTyping();

		GlStateManager.pushMatrix();

		GlStateManager.disableDepth();

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager
				.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
						GlStateManager.SourceFactor.ZERO,
						GlStateManager.DestFactor.ONE);

		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GlStateManager.enableAlpha();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(HUE_BACKGROUND);

		drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width,
				this.height,
				this.width, this.height);

		GlStateManager.enableDepth();

		GlStateManager.enableBlend();

		GlStateManager.popMatrix();

		if (this.mc.world != null)
		{
			if (!MathUtil.epsilonEquals(PERCENT, this.lastPercent) && PERCENT > 0.0F)
			{
				this.lastPercent = PERCENT;

				String percentString = String.valueOf(MathHelper.floor(PERCENT));

				this.loading.setText(new Text(new TextComponentTranslation("gui.aether.loading.progress", percentString), 1.0F));
			}
		}

		super.drawElements();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		ClientEventHandler.drawFade(false);
	}

	@Override
	public void drawCustomLoading()
	{
		this.drawScreen(InputHelper.getMouseX(), InputHelper.getMouseY(), PartialTicks.get());
	}
}
