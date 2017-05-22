package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import com.gildedgames.aether.common.entities.living.npc.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import org.lwjgl.Sys;

import java.io.IOException;
import java.util.Collection;

public class GuiDialogViewer extends GuiScreen implements IDialogChangeListener
{

	private static final ResourceLocation NEXT_ARROW = AetherCore.getResource("textures/gui/conversation/next_arrow.png");

	private final IDialogController controller;

	private GuiTextBox topTextBox, bottomTextBox, namePlate;

	private double nextArrowAnim, prevTime;

	private boolean canApplyNextAction;

	private EntityNPC npc;

	private IDialogNode node;

	private IDialogSpeaker speaker;

	public GuiDialogViewer(IDialogController controller)
	{
		this.controller = controller;
		this.controller.addListener(this);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		if (this.npc == null)
		{
			this.npc = new EntityEdison(this.mc.world);
		}

		GlStateManager.pushMatrix();

		GlStateManager.translate(0, 0, 100F);
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		GuiInventory.drawEntityOnScreen(
				(this.width / 2) + 40, this.height, 120, -mouseX + (this.width / 2) + 40, (-mouseY / 10.0F), this.npc);

		GlStateManager.translate(0, 0, 100F);

		Gui.drawRect(0, this.height - 90, this.width, this.height, Integer.MIN_VALUE);

		super.drawScreen(mouseX, mouseY, partialTicks);

		GlStateManager.popMatrix();

		if (!this.controller.isNodeFinished() || (this.controller.isNodeFinished() && this.node.getButtons().isEmpty()))
		{
			GlStateManager.pushMatrix();

			double time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
			double timePassed = time - this.prevTime;

			this.prevTime = time;

			if (this.nextArrowAnim < 1000)
			{
				this.nextArrowAnim += timePassed;
			}
			else
			{
				this.nextArrowAnim = 0.0;
			}

			double anim = this.nextArrowAnim;

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
						this.topTextBox.xPosition + this.topTextBox.width + 5,
						this.topTextBox.yPosition + this.topTextBox.height - 20, 0, 0, 13, 12, 13, 12);
			}
			else
			{
				Gui.drawModalRectWithCustomSizedTexture(
						this.bottomTextBox.xPosition + this.bottomTextBox.width,
						this.bottomTextBox.yPosition + this.bottomTextBox.height - 20, 0, 0, 13, 12, 13, 12);
			}

			GlStateManager.popMatrix();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button instanceof GuiDialogButton)
		{
			GuiDialogButton dialogButton = (GuiDialogButton) button;

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

	private void buildGui(IDialogNode node)
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

		Collection<IDialogButton> buttons = node.getButtons();

		int baseBoxSize = 350;
		boolean resize = this.width - 40 > baseBoxSize;

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

			for (IDialogButton btn : buttons)
			{
				this.buttonList.add(new GuiDialogButton(index, (this.width / 2) - (baseBoxSize / 2), this.height - 85 + (index * 20), btn));

				index++;
			}
		}

		IDialogLine line = this.controller.getCurrentLine();

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
			ResourceLocation speakerPath = this.controller.getCurrentLine().getSpeaker().get();

			this.speaker = AetherAPI.content().dialog().getSpeaker(speakerPath).orElseThrow(() ->
					new IllegalArgumentException("Couldn't getByte speaker " + speakerPath));

			this.fontRenderer = Minecraft.getMinecraft().fontRenderer;

			boolean topText = this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0;

			String name = I18n.format(this.speaker.getUnlocalizedName());

			this.namePlate = new GuiTextBox(
					buttons.size() + 2,
					resize ? (this.width / 2) - (baseBoxSize / 2) : 20,
					this.height - (topText ? 122 + this.topTextBox.getTextHeight(this.fontRenderer) : 107),
					this.fontRenderer.getStringWidth(name + 10), 20);

			ITextComponent textComponent = new TextComponentTranslation(name);
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
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		this.nextAction();
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
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
