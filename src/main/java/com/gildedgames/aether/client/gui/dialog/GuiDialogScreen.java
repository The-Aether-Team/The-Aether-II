package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.ContainerDialogController;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class GuiDialogScreen extends ContainerScreen<ContainerDialogController> implements IDialogChangeListener
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

	private double currentScroll, maxScroll;

	private ISceneInstance sceneInstance;

	public GuiDialogScreen(ContainerDialogController container, PlayerInventory playerInventory, IDialogController controller, ISceneInstance sceneInstance)
	{
		super(container, playerInventory, new StringTextComponent("Dialog"));

		this.sceneInstance = sceneInstance;

		this.controller = controller;
		this.controller.addListener(this);
	}

	@Override
	public void render(final int mouseX, final int mouseY, final float partialTicks)
	{
		this.renderBackground(0);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this));

		GlStateManager.pushMatrix();

		GlStateManager.disableDepthTest();

		GlStateManager.translatef(0, 0, 100F);
		GlStateManager.color3f(1.0F, 1.0F, 1.0F);

		if (this.slide != null && this.renderer != null)
		{
			this.renderer.draw(this.slide, this.width, this.height, mouseX, mouseY, partialTicks);
		}

		GlStateManager.translatef(0, 0, 100F);

		AbstractGui.fill(0, this.height - 90, this.width, this.height, Integer.MIN_VALUE);

		if (this.maxScroll > 0 && this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0)
		{
			int baseBoxSize = 350;
			int x = (this.width / 2) - (baseBoxSize / 2);
			int y = this.height - 85;

			AbstractGui.fill(x - 17, this.height - 85, x - 7, this.height - 10, Integer.MIN_VALUE);

			int scrollYOffset = this.currentScroll == 0 ? 0 : MathHelper.floor((float) this.currentScroll * (55.0F / (float) this.maxScroll));

			AbstractGui.fill(x - 17, y + scrollYOffset, x - 7, y + 20 + scrollYOffset, 0x96FFFFFF);
		}

		super.render(mouseX, mouseY, partialTicks);

		GlStateManager.enableDepthTest();

		GlStateManager.popMatrix();

		if (!this.controller.isNodeFinished() || this.node.getButtons().isEmpty())
		{
			GlStateManager.pushMatrix();

			GlStateManager.disableDepthTest();

			final double time = Util.milliTime();
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

			final float anim = (float) this.nextArrowAnim;

			if (this.nextArrowAnim < 500.0)
			{
				GlStateManager.translatef(0, anim / 500.0f, 0);
			}
			else if (this.nextArrowAnim >= 500.0)
			{
				GlStateManager.translatef(0, -((anim - 500.0f) / 500.0f), 0);
			}

			GlStateManager.translatef(0, 0, 302F);
			GlStateManager.color3f(1.0F, 1.0F, 1.0F);

			Minecraft.getInstance().getTextureManager().bindTexture(NEXT_ARROW);

			if (this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0)
			{
				AbstractGui.blit(
						this.topTextBox.x + this.topTextBox.getWidth() + 5,
						this.topTextBox.y + this.topTextBox.getHeight() - 20, 0, 0, 13, 12, 13, 12);
			}
			else
			{
				AbstractGui.blit(
						this.bottomTextBox.x + this.bottomTextBox.getWidth(),
						this.bottomTextBox.y + this.bottomTextBox.getHeight() - 20, 0, 0, 13, 12, 13, 12);
			}

			GlStateManager.enableDepthTest();

			GlStateManager.popMatrix();
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{

	}

	@Override
	public void renderBackground()
	{

	}

	private void onDialogButtonPressed(final Button button)
	{
		if (button instanceof GuiDialogButton)
		{
			final GuiDialogButton dialogButton = (GuiDialogButton) button;

			this.controller.activateButton(dialogButton.getButtonData());

			if (this.controller.getCurrentScene() == null)
			{
				Minecraft.getInstance().displayGuiScreen(null);
			}
		}
	}

	@Override
	public void init()
	{
		super.init();

		final IDialogNode node1 = this.controller.getCurrentNode();

		if (this.node != node1)
		{
			this.canApplyNextAction = false;
		}

		this.node = node1;

		if (this.minecraft == null)
		{
			return;
		}

		final Collection<IDialogButton> beforeConditionButtons = node1.getButtons();

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

			for (final IDialogButton dialogButton : beforeConditionButtons)
			{
				if (this.controller.conditionsMet(dialogButton))
				{
					GuiDialogButton button = new GuiDialogButton((this.width / 2) - (baseBoxSize / 2), this.height - 85 + (index * 20), dialogButton, (_btn) -> {
						this.controller.activateButton(dialogButton);

						if (this.controller.getCurrentScene() == null)
						{
							Minecraft.getInstance().displayGuiScreen(null);
						}
					});

					button.visible = false;
					button.active = false;

					this.addButton(button);

					this.buttons.add(button);

					index++;
				}
			}
		}

		this.currentScroll = 0;

		this.topTextBox = new GuiTextBox(resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 175, baseBoxSize, 80);
		this.bottomTextBox = new GuiTextBox(resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 85, baseBoxSize, 70);

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
			if (speakerPath.getPath().contains("#"))
			{
				// Obtain the slide address from the Speaker resourcelocation
				address = speakerPath.getPath().substring(speakerPath.getPath().indexOf("#") + 1);

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

			this.font = Minecraft.getInstance().fontRenderer;

			final boolean topText = this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0;

			final String name = I18n.format(this.speaker.getUnlocalizedName());

			this.namePlate = new GuiTextBox(
					resize ? (this.width / 2) - (baseBoxSize / 2) : 20,
					this.height - (topText ? 122 + this.topTextBox.getTextHeight(this.font) : 107),
					this.font.getStringWidth(name + 10), 20);

			final ITextComponent textComponent = new TranslationTextComponent(name);
			textComponent.setStyle(new Style().setColor(TextFormatting.YELLOW).setItalic(true));

			this.namePlate.setText(textComponent);

			this.addButton(this.namePlate);
		}

		this.addButton(this.topTextBox);
		this.addButton(this.bottomTextBox);
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
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_)
	{
		if (!super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_))
		{
			this.nextAction();
		}

		return true;
	}

	@Override
	public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_)
	{
		if (!super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_))
		{
			this.nextAction();
		}

		return true;
	}

	@Override
	public void onDialogChanged()
	{
		this.init();
	}

	@Override
	public void beforeSceneCloses()
	{

	}

	private void refreshButtonsWithScroll()
	{
		for (int i = 0; i < this.buttons.size(); i++)
		{
			Widget button = this.buttons.get(i);

			if (i >= this.currentScroll && i <= this.currentScroll + 3)
			{
				button.y = this.height - 85 + ((i - (int) this.currentScroll) * 20);

				button.active = true;
				button.visible = true;
			}
			else
			{
				button.active = false;
				button.visible = false;
			}
		}
	}

	@Override
	public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double amount)
	{
		if (!super.mouseScrolled(p_mouseScrolled_1_, p_mouseScrolled_3_, amount))
		{
			if (amount != 0)
			{
				this.currentScroll = MathHelper.clamp(this.currentScroll - amount, 0.0, this.maxScroll);

				this.refreshButtonsWithScroll();
			}
		}

		return true;
	}


	@Override
	public void onClose()
	{
		super.onClose();

		if (this.controller.getCurrentScene() != null)
		{
			if (preventDialogControllerClose)
			{
				preventDialogControllerClose = false;
			}
			else
			{
				this.controller.closeScene(this.sceneInstance);
			}
		}
	}
}
