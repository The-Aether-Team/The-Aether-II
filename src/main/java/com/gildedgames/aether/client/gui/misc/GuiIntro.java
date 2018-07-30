package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.client.ClientEventHandler;
import com.gildedgames.aether.client.gui.dialog.GuiNextArrow;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketCancelIntro;
import com.gildedgames.aether.common.network.packets.PacketSetPlayedIntro;
import com.gildedgames.orbis_api.client.gui.data.Text;
import com.gildedgames.orbis_api.client.gui.util.GuiText;
import com.gildedgames.orbis_api.client.gui.util.GuiTextBox;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiViewerNoContainer;
import com.gildedgames.orbis_api.client.gui.util.gui_library.IGuiContext;
import com.gildedgames.orbis_api.client.gui.util.vanilla.GuiButtonVanilla;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiIntro extends GuiViewerNoContainer
{
	private static final ResourceLocation GILDED_GAMES = AetherCore.getResource("textures/gui/intro/gg_logo.png");

	private static final ResourceLocation HIGHLANDS = AetherCore.getResource("textures/gui/intro/highlands.png");

	private static final ResourceLocation HUE_BACKGROUND = AetherCore.getResource("textures/gui/intro/hue_background.png");

	private int tipIndex = 0;

	private GuiTexture ggLogo, highlands;

	private GuiNextArrow nextArrow;

	private GuiText proudlyPresents, holdToSkip;

	private GuiTextBox prologue, tip1, tip2, tip3, tip4;

	private GuiButtonVanilla yes, no;

	private long timeStarted, timeSinceHoldSkip, timeSinceStopSkip;

	private boolean playedMusic, startIntro, holding;

	private int keyHeld = Keyboard.KEY_NONE;

	public GuiIntro()
	{
		super(new GuiElement(Dim2D.flush(), false));
	}

	private double getSecondsSinceStopSkip()
	{
		return (System.currentTimeMillis() - this.timeSinceStopSkip) / 1000.0D;
	}

	private double getSecondsSinceHoldSkip()
	{
		return (System.currentTimeMillis() - this.timeSinceHoldSkip) / 1000.0D;
	}

	private double getSecondsSinceStart()
	{
		return (System.currentTimeMillis() - this.timeStarted) / 1000.0D;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		AetherMusicManager.INSTANCE.stopMusic();
	}

	@Override
	public void build(IGuiContext context)
	{
		this.getViewing().dim().mod().width(this.width).height(this.height).flush();

		final Pos2D center = InputHelper.getCenter();

		this.ggLogo = new GuiTexture(Dim2D.build().scale(0.5F).width(256).height(242).center(true).pos(center).flush(), GILDED_GAMES);
		this.highlands = new GuiTexture(Dim2D.build().scale(0.45F).width(512).height(235).center(true).pos(center).flush(), HIGHLANDS);

		this.proudlyPresents = new GuiText(Dim2D.build().center(true).pos(center).addY(70).flush(),
				new Text(new TextComponentTranslation("intro.proudlyPresents"), 1.0F));

		this.holdToSkip = new GuiText(Dim2D.build().pos(this.width - 65, this.height - 17).flush(),
				new Text(new TextComponentTranslation("intro.holdToSkip"), 1.0F));

		this.holdToSkip.build(this);

		this.holdToSkip.state().setVisible(false);

		this.prologue = new GuiTextBox(Dim2D.build().center(true).pos(center).width(300).addY(30).flush(), false,
				new Text(new TextComponentTranslation(
						"intro.prologue"),
						1.0F));

		this.tip1 = new GuiTextBox(Dim2D.build().center(true).pos(center).width(300).flush(), false,
				new Text(new TextComponentTranslation(
						"intro.tip1"),
						1.0F));

		this.tip2 = new GuiTextBox(Dim2D.build().center(true).pos(center).width(300).flush(), false,
				new Text(new TextComponentTranslation(
						"intro.tip2"),
						1.0F));

		this.tip3 = new GuiTextBox(Dim2D.build().center(true).pos(center).width(300).flush(), false,
				new Text(new TextComponentTranslation(
						"intro.tip3"),
						1.0F));

		this.tip4 = new GuiTextBox(Dim2D.build().center(true).pos(center).width(300).flush(), false,
				new Text(new TextComponentTranslation(
						"intro.tip4"),
						1.0F));

		this.ggLogo.build(this);

		this.ggLogo.state().setVisible(false);

		this.nextArrow = new GuiNextArrow();

		this.nextArrow.dim().mod().center(true).pos(center).addY(-7).addX(-170).flush();

		this.proudlyPresents.dim().mod().addX(3).flush();

		this.yes = new GuiButtonVanilla(Dim2D.build().width(80).height(20).center(true).pos(center).addX(5).flush());
		this.no = new GuiButtonVanilla(Dim2D.build().width(80).height(20).center(true).pos(center).addX(95).flush());

		this.yes.getInner().displayString = I18n.format("intro.yes");
		this.no.getInner().displayString = I18n.format("intro.no");

		context.addChildren(this.ggLogo, this.proudlyPresents, this.highlands, this.prologue, this.tip1, this.tip2, this.tip3, this.tip4, this.nextArrow,
				this.holdToSkip, this.yes, this.no);

		this.proudlyPresents.state().setVisible(false);
		this.prologue.state().setVisible(false);
		this.highlands.state().setVisible(false);

		this.nextArrow.state().setVisible(false);
		this.tip1.state().setVisible(false);
		this.tip2.state().setVisible(false);
		this.tip3.state().setVisible(false);
		this.tip4.state().setVisible(false);

		this.yes.state().setVisible(false);
		this.no.state().setVisible(false);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		final int bg = 0xFF000000;

		this.drawGradientRect(0, 0, this.width, this.height, bg, bg);

		preventInnerTyping();

		if (this.startIntro && this.holding && !Keyboard.isKeyDown(this.keyHeld) && this.keyHeld != Keyboard.KEY_NONE)
		{
			this.timeSinceStopSkip = System.currentTimeMillis();
			this.holding = false;
			this.keyHeld = Keyboard.KEY_NONE;
		}

		if (!this.startIntro)
		{
			this.tip1.state().setVisible(this.tipIndex == 0);
			this.tip2.state().setVisible(this.tipIndex == 1);
			this.tip3.state().setVisible(this.tipIndex == 2);
			this.tip4.state().setVisible(this.tipIndex == 3);

			this.nextArrow.state().setVisible(this.tipIndex != 3);

			this.yes.state().setVisible(this.tipIndex == 3);
			this.no.state().setVisible(this.tipIndex == 3);

			super.drawScreen(mouseX, mouseY, partialTicks);

			return;
		}

		if (this.getSecondsSinceStart() >= 1)
		{
			final float alpha = (float) Math.min(1.0F, this.getSecondsSinceHoldSkip());

			if (this.holding)
			{
				this.holdToSkip.state().setAlpha(alpha);
			}
			else
			{
				final float combined = (float) Math.max(0.0F, alpha - this.getSecondsSinceStopSkip());

				this.holdToSkip.state().setAlpha(combined);
			}

			this.holdToSkip.state().setVisible(true);

			if (this.holding && this.getSecondsSinceHoldSkip() >= 2.5)
			{
				ClientEventHandler.drawBlackFade(10.0D);
				Minecraft.getMinecraft().displayGuiScreen(null);

				Minecraft.getMinecraft().getSoundHandler().stopSounds();

				PlayerAether.getPlayer(this.mc.player).getTeleportingModule().setPlayedIntro(true);
				NetworkingAether.sendPacketToServer(new PacketSetPlayedIntro(true));

				ClientEventHandler.setDrawBlackScreen(false);

				return;
			}
		}

		if (this.getSecondsSinceStart() <= 15)
		{
			final float fade = Math.min(1.0F, (float) ((this.getSecondsSinceStart()) / 15.0D));

			this.ggLogo.state().setAlpha(fade);
			this.ggLogo.state().setVisible(true);

			if (this.getSecondsSinceStart() >= 10)
			{
				this.proudlyPresents.state().setAlpha(Math.min(1.0F, Math.max(0.0F, (float) ((this.getSecondsSinceStart() - 10) / 5.0D))));

				this.proudlyPresents.state().setVisible(true);
			}
			else
			{
				this.proudlyPresents.state().setVisible(false);
			}
		}
		else if (this.getSecondsSinceStart() <= 20)
		{
			final float fade = Math.max(0.0F, 1.0F - Math.min(1.0F, (float) ((this.getSecondsSinceStart() - 15) / 5.0D)));

			this.ggLogo.state().setAlpha(fade);
			this.proudlyPresents.state().setAlpha(fade);
		}
		else
		{
			this.ggLogo.state().setVisible(false);
			this.proudlyPresents.state().setVisible(false);
		}

		if (this.getSecondsSinceStart() >= 20 && this.getSecondsSinceStart() < 30)
		{
			final float alpha = (float) Math.min(1.0F, (this.getSecondsSinceStart() - 20) / 10.0D);

			this.highlands.state().setAlpha(alpha);

			this.highlands.state().setVisible(true);
		}

		if (this.getSecondsSinceStart() >= 30)
		{
			final float dif = (float) ((this.getSecondsSinceStart() - 30D) * 10.0D);

			this.highlands.dim().mod().y(InputHelper.getCenter().y() - Math.min(55, dif)).flush();

			GlStateManager.pushMatrix();

			float alpha = Math.min(1.0F, (float) ((this.getSecondsSinceStart() - 30D) / 10.0D));
			float pAlpha = Math.min(1.0F, (float) ((this.getSecondsSinceStart() - 34D) / 8.5D));

			if (this.getSecondsSinceStart() >= 42.5D)
			{
				pAlpha = alpha = Math.min(1.0F, 1.0F - (float) ((this.getSecondsSinceStart() - 42.5D) / 10.0D));

				this.highlands.state().setAlpha(alpha);
			}

			this.prologue.state().setAlpha(pAlpha);
			this.prologue.state().setVisible(true);

			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager
					.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
							GlStateManager.SourceFactor.ZERO,
							GlStateManager.DestFactor.ONE);

			GL11.glEnable(GL11.GL_ALPHA_TEST);

			GlStateManager.enableAlpha();

			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

			this.mc.getTextureManager().bindTexture(HUE_BACKGROUND);

			drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width,
					this.height,
					this.width, this.height);

			GlStateManager.popMatrix();
		}

		if (this.getSecondsSinceStart() >= 60)
		{
			PlayerAether.getPlayer(this.mc.player).getTeleportingModule().setPlayedIntro(true);
			NetworkingAether.sendPacketToServer(new PacketSetPlayedIntro(true));

			ClientEventHandler.setDrawBlackScreen(false);

			ClientEventHandler.drawBlackFade(10.0D);
			Minecraft.getMinecraft().displayGuiScreen(null);
		}

		if (!this.playedMusic)
		{
			Minecraft.getMinecraft().getSoundHandler().stopSounds();
			AetherMusicManager.INSTANCE.playMusic(new SoundEvent(AetherCore.getResource("music.intro")));
			this.playedMusic = true;
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);

		if (!this.startIntro)
		{
			this.advanceTips();
		}
		else
		{
			this.timeSinceHoldSkip = System.currentTimeMillis();
			this.holding = true;
			this.keyHeld = keyCode;
		}
	}

	private void advanceTips()
	{
		if (this.tipIndex == 3)
		{
			if (InputHelper.isHovered(this.no))
			{
				NetworkingAether.sendPacketToServer(new PacketCancelIntro());
				Minecraft.getMinecraft().displayGuiScreen(new GuiBlackScreen());

				ClientEventHandler.setDrawBlackScreen(false);

				return;
			}

			if (!InputHelper.isHovered(this.yes))
			{
				return;
			}
		}

		if (this.tipIndex >= 3)
		{
			this.nextArrow.state().setVisible(false);
			this.tip4.state().setVisible(false);
			this.yes.state().setVisible(false);
			this.no.state().setVisible(false);

			this.startIntro = true;

			this.timeStarted = System.currentTimeMillis();
		}

		this.tipIndex++;
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (!this.startIntro)
		{
			this.advanceTips();
		}
		else
		{
			this.timeSinceHoldSkip = System.currentTimeMillis();
			this.holding = true;
		}

	}

	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state)
	{
		super.mouseReleased(mouseX, mouseY, state);

		if (this.startIntro)
		{
			this.timeSinceStopSkip = System.currentTimeMillis();
			this.holding = false;
		}
	}

}
