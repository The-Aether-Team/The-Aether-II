package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.ContainerDialogController;
import com.gildedgames.orbis_api.client.gui.util.GuiFrame;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class GuiDialogViewer extends GuiFrame implements IDialogChangeListener
{

	private static final ResourceLocation NEXT_ARROW = AetherCore.getResource("textures/gui/conversation/next_arrow.png");

	public static boolean preventDialogControllerClose;

	private final IDialogController controller;

	private GuiTextBox topTextBox, bottomTextBox, namePlate;

	private double nextArrowAnim, prevTime;

	private boolean canApplyNextAction = true;

	private IDialogNode node;

	private IDialogSpeaker speaker;

	private IDialogSlide slide;

	private IDialogSlideRenderer renderer;

	private int currentScroll, maxScroll;

	private LinkedList<GuiDialogButton> buttons = Lists.newLinkedList();

	public GuiDialogViewer(final EntityPlayer player, final IDialogController controller)
	{
		super(null, Dim2D.flush(), new ContainerDialogController(player));

		this.controller = controller;
		this.controller.addListener(this);
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		this.drawWorldBackground(0);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this));

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

		if (this.maxScroll > 0 && this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0)
		{
			int baseBoxSize = 350;
			int x = (this.width / 2) - (baseBoxSize / 2);
			int y = this.height - 85;

			Gui.drawRect(x - 17, this.height - 85, x - 7, this.height - 10, Integer.MIN_VALUE);

			int scrollYOffset = this.currentScroll == 0 ? 0 : MathHelper.floor((float) this.currentScroll * (55.0F / (float) this.maxScroll));

			Gui.drawRect(x - 17, y + scrollYOffset, x - 7, y + 20 + scrollYOffset, 0x96FFFFFF);
		}

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
	public void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{

	}

	@Override
	public void drawDefaultBackground()
	{

	}

	@Override
	protected void actionPerformed(final GuiButton button)
	{
		if (button instanceof GuiDialogButton)
		{
			final GuiDialogButton dialogButton = (GuiDialogButton) button;

			this.controller.activateButton(dialogButton.getButtonData());

			if (this.controller.getCurrentScene() == null)
			{
				Minecraft.getMinecraft().displayGuiScreen(null);
			}
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();

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

		final Collection<IDialogButton> beforeConditionButtons = node.getButtons();

		int baseBoxSize = 350;
		final boolean resize = this.width - 40 > baseBoxSize;

		if (!resize)
		{
			baseBoxSize = this.width - 40;
		}

		if (this.controller.isNodeFinished())
		{
			this.buttons.clear();

			int index = 0;

			for (final IDialogButton btn : beforeConditionButtons)
			{
				if (this.controller.conditionsMet(btn))
				{
					GuiDialogButton button = new GuiDialogButton(index, (this.width / 2) - (baseBoxSize / 2), this.height - 85 + (index * 20), btn);

					button.visible = false;
					button.enabled = false;

					this.buttonList.add(button);
					this.buttons.add(button);

					index++;
				}
			}
		}

		this.currentScroll = 0;

		this.topTextBox = new GuiTextBox(this.buttons.size(),
				resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 175, baseBoxSize, 80);
		this.bottomTextBox = new GuiTextBox(
				this.buttons.size() + 1, resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 85, baseBoxSize, 70);

		this.topTextBox.showBackdrop = true;
		this.topTextBox.bottomToTop = true;

		this.refreshButtonsWithScroll();

		this.maxScroll = Math.max(0, this.buttons.size() - 4);

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
					this.buttons.size() + 2,
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
		super.keyTyped(typedChar, keyCode);

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

	@Override
	public void init()
	{
		this.dim().mod().width(this.width).height(this.height).flush();
	}

	private void refreshButtonsWithScroll()
	{
		for (int i = 0; i < this.buttons.size(); i++)
		{
			GuiDialogButton button = this.buttons.get(i);

			if (i >= this.currentScroll && i <= this.currentScroll + 3)
			{
				button.y = this.height - 85 + ((i - this.currentScroll) * 20);

				button.enabled = true;
				button.visible = true;
			}
			else
			{
				button.enabled = false;
				button.visible = false;
			}
		}
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();

		int scroll = MathHelper.clamp(Mouse.getEventDWheel(), -1, 1);

		if (scroll != 0)
		{
			this.currentScroll = MathHelper.clamp(this.currentScroll - scroll, 0, this.maxScroll);

			this.refreshButtonsWithScroll();
		}
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		if (this.controller.getCurrentScene() != null)
		{
			if (preventDialogControllerClose)
			{
				preventDialogControllerClose = false;
			}
			else
			{
				this.controller.closeScene();
			}
		}
	}
}
