package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.ContainerDialogController;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import com.gildedgames.aether.common.entities.living.npc.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.Sys;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class GuiDialogViewer extends GuiContainer implements IDialogChangeListener
{

	private static final ResourceLocation NEXT_ARROW = AetherCore.getResource("textures/gui/conversation/next_arrow.png");

	private final IDialogController controller;

	private GuiTextBox topTextBox, bottomTextBox, namePlate;

	private double nextArrowAnim, prevTime;

	private boolean canApplyNextAction;

	private EntityNPC npc;

	private IDialogNode node;

	private IDialogSpeaker speaker;

	private IDialogSlide slide;

	private IDialogSlideRenderer renderer;

	public GuiDialogViewer(final EntityPlayer player, final IDialogController controller)
	{
		super(new ContainerDialogController(player));

		this.controller = controller;
		this.controller.addListener(this);
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		this.drawWorldBackground(0);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this));

		if (this.npc == null)
		{
			this.npc = new EntityEdison(this.mc.world);
		}

		GlStateManager.pushMatrix();

        GlStateManager.disableDepth();

		GlStateManager.translate(0, 0, 100F);
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		if (this.slide != null && this.renderer != null)
		{
			this.renderer.draw(this.slide, this.width, this.height, mouseX, mouseY, partialTicks);
		}

		GlStateManager.translate(0, 0, 100F);

		Gui.drawRect(0, this.height - 90, this.width, this.height, Integer.MIN_VALUE);

		super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.enableDepth();

		GlStateManager.popMatrix();

		if (!this.controller.isNodeFinished() || (this.controller.isNodeFinished() && this.node.getButtons().isEmpty()))
		{
			GlStateManager.pushMatrix();

            GlStateManager.disableDepth();

			final double time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
			final double timePassed = time - this.prevTime;

			this.prevTime = time;

			if (this.nextArrowAnim < 1000)
			{
				this.nextArrowAnim += timePassed;
			}
			else
			{
				this.nextArrowAnim = 0.0;
			}

			final double anim = this.nextArrowAnim;

			if (this.nextArrowAnim < 500.0)
			{
				GlStateManager.translate(0, anim / 500.0, 0);
			}
			else if (this.nextArrowAnim >= 500.0)
			{
				GlStateManager.translate(0, -((anim - 500.0) / 500.0), 0);
			}

			GlStateManager.translate(0, 0, 302F);
			GlStateManager.color(1.0F, 1.0F, 1.0F);

			Minecraft.getMinecraft().renderEngine.bindTexture(NEXT_ARROW);

			if (this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0)
			{
				Gui.drawModalRectWithCustomSizedTexture(
						this.topTextBox.x + this.topTextBox.width + 5,
						this.topTextBox.y + this.topTextBox.height - 20, 0, 0, 13, 12, 13, 12);
			}
			else
			{
				Gui.drawModalRectWithCustomSizedTexture(
						this.bottomTextBox.x + this.bottomTextBox.width,
						this.bottomTextBox.y + this.bottomTextBox.height - 20, 0, 0, 13, 12, 13, 12);
			}

			GlStateManager.enableDepth();

			GlStateManager.popMatrix();
		}
	}

	@Override
	public void drawDefaultBackground()
	{

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{

	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException
	{
		if (button instanceof GuiDialogButton)
		{
			final GuiDialogButton dialogButton = (GuiDialogButton) button;

			this.controller.activateButton(dialogButton.getButtonData());

			if (this.controller.getCurrentScene() == null)
			{
				Minecraft.getMinecraft().displayGuiScreen(null);
				return;
			}
		}
	}

	@Override
	public void initGui()
	{
		this.buildGui(this.controller.getCurrentNode());
	}

	private void resetGui()
	{
		this.buttonList.clear();
	}

	private void buildGui(final IDialogNode node)
	{
		if (this.node != node)
		{
			this.canApplyNextAction = false;
		}

		this.node = node;

		this.resetGui();

		if (this.mc == null)
		{
			return;
		}

		final Collection<IDialogButton> buttons = node.getButtons();

		int baseBoxSize = 350;
		final boolean resize = this.width - 40 > baseBoxSize;

		if (!resize)
		{
			baseBoxSize = this.width - 40;
		}

		this.topTextBox = new GuiTextBox(buttons.size(),
				resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 175, baseBoxSize, 80);
		this.bottomTextBox = new GuiTextBox(
				buttons.size() + 1, resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 85, baseBoxSize, 70);

		this.topTextBox.showBackdrop = true;
		this.topTextBox.bottomToTop = true;

		if (this.controller.isNodeFinished())
		{
			int index = 0;

			for (final IDialogButton btn : buttons)
			{
				this.buttonList.add(new GuiDialogButton(index, (this.width / 2) - (baseBoxSize / 2), this.height - 85 + (index * 20), btn));

				index++;
			}
		}

		final IDialogLine line = this.controller.getCurrentLine();

		if (this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0)
		{
			this.topTextBox.setText(line.getLocalizedBody());
		}
		else
		{
			this.bottomTextBox.setText(line.getLocalizedBody());
		}

		if (this.controller.getCurrentLine().getSpeaker().isPresent())
		{
			final ResourceLocation speakerPath = this.controller.getCurrentLine().getSpeaker().get();

			this.speaker = AetherAPI.content().dialog().getSpeaker(speakerPath).orElseThrow(() ->
					new IllegalArgumentException("Couldn't getByte speaker: " + speakerPath));

			final String address;

			// Check if the speaker resourcelocation has a slide address
			if (speakerPath.getResourcePath().contains("#"))
			{
				// Obtain the slide address from the Speaker resourcelocation
				address = speakerPath.getResourcePath().substring(speakerPath.getResourcePath().indexOf("#") + 1,
						speakerPath.getResourcePath().length());

				this.slide = AetherAPI.content().dialog().findSlide(address, this.speaker).orElseThrow(() ->
						new IllegalArgumentException("Couldn't find slide: " + address));
			}
			else if (this.speaker.getSlides().isPresent())
			{
				final Map<String, IDialogSlide> slides = this.speaker.getSlides().get();

				if (!slides.isEmpty() && slides.containsKey("default"))
				{
					this.slide = slides.get("default");
				}
			}

			if (this.slide != null && this.slide.getRenderer().isPresent())
			{
				final String renderType = this.slide.getRenderer().get();

				this.renderer = AetherAPI.content().dialog().findRenderer(renderType).orElseThrow(() ->
						new IllegalArgumentException("Couldn't find slide renderer: " + renderType));

				this.renderer.setup(this.slide);
			}

			this.fontRenderer = Minecraft.getMinecraft().fontRenderer;

			final boolean topText = this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0;

			final String name = I18n.format(this.speaker.getUnlocalizedName());

			this.namePlate = new GuiTextBox(
					buttons.size() + 2,
					resize ? (this.width / 2) - (baseBoxSize / 2) : 20,
					this.height - (topText ? 122 + this.topTextBox.getTextHeight(this.fontRenderer) : 107),
					this.fontRenderer.getStringWidth(name + 10), 20);

			final ITextComponent textComponent = new TextComponentTranslation(name);
			textComponent.setStyle(new Style().setColor(TextFormatting.YELLOW).setItalic(true));

			this.namePlate.setText(textComponent);

			this.buttonList.add(this.namePlate);
		}

		this.buttonList.add(this.topTextBox);
		this.buttonList.add(this.bottomTextBox);
	}

	private void nextAction()
	{
		if (!this.canApplyNextAction)
		{
			this.canApplyNextAction = true;

			return;
		}

		this.controller.advance();
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException
	{
		this.nextAction();
	}

	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state)
	{
		this.nextAction();

		super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	public void onDialogChanged()
	{
		this.buildGui(this.controller.getCurrentNode());
	}

	@Override
	public void onSceneClosed()
	{
		Minecraft.getMinecraft().displayGuiScreen(null);
	}
}
