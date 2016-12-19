package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.ContainerDialogController;
import com.gildedgames.aether.common.dialog.*;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import com.gildedgames.aether.common.entities.living.npc.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.Sys;

import java.io.IOException;
import java.util.Collection;

public class GuiDialogController extends GuiContainer implements IDialogController
{

	private static final ResourceLocation NEXT_ARROW = AetherCore.getResource("textures/gui/dialog/next_arrow.png");

	private final EntityPlayer player;

	private DialogTree scene;

	private IDialogNode node;

	private GuiTextBox topTextBox, bottomTextBox, namePlate;

	private int textIndex;

	private double nextArrowAnim, prevTime;

	private boolean canApplyNextAction;

	private EntityNPC npc;

	public GuiDialogController(EntityPlayer player)
	{
		super(new ContainerDialogController(player));

		this.player = player;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		if (this.textIndex < this.node.getContent().size())
		{
			int i = 0;

			for (IDialogContent c : this.node.getContent())
			{
				if (i == this.textIndex)
				{
					GlStateManager.pushMatrix();

					Minecraft.getMinecraft().renderEngine.bindTexture(c.getPortrait());

					double scale = 1.6;

					double scaledWidth = 110 * scale;
					double scaledHeight = 98 * scale;

					GlStateManager.translate((this.width / 2) - (scaledWidth / 2), this.height - 90 - scaledHeight, 0);
					GlStateManager.scale(scale, scale, scale);

					Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 110, 98, 110, 98);
					GlStateManager.popMatrix();
					break;
				}

				i++;
			}
		}

		GlStateManager.pushMatrix();

		Gui.drawRect(0, this.height - 90, this.width, this.height, Integer.MIN_VALUE);

		GlStateManager.popMatrix();

		if (this.textIndex + 1 < this.node.getContent().size() || this.node.getButtons().isEmpty())
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

			if (this.textIndex + 1 >= this.node.getContent().size() && this.node.getButtons().size() > 0)
			{
				Gui.drawModalRectWithCustomSizedTexture(this.topTextBox.xPosition + this.topTextBox.width + 5, this.topTextBox.yPosition + this.topTextBox.height - 20, 0, 0, 13, 12, 13, 12);
			}
			else
			{
				Gui.drawModalRectWithCustomSizedTexture(this.bottomTextBox.xPosition + this.bottomTextBox.width, this.bottomTextBox.yPosition + this.bottomTextBox.height - 20, 0, 0, 13, 12, 13, 12);
			}

			GlStateManager.popMatrix();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button instanceof GuiDialogButton)
		{
			GuiDialogButton dialogButton = (GuiDialogButton)button;

			dialogButton.getButtonData().onClicked(this);
		}
	}

	@Override
	public void initGui()
	{
		this.buildGui(this.node);
	}

	@Override
	public void show(DialogTree scene)
	{
		this.scene = scene;

		this.buildGui(scene.getRootNode());
	}

	@Override
	public DialogTree getCurrentScene()
	{
		return this.scene;
	}

	@Override
	public void setNode(String nodeId)
	{
		IDialogNode node = this.scene.getNode(nodeId);

		if (node == null)
		{
			throw new IllegalArgumentException("Node with name '" + nodeId + "' not found");
		}

		this.buildGui(node);
	}

	@Override
	public void close()
	{
		Minecraft.getMinecraft().displayGuiScreen(null);
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
			this.textIndex = 0;
		}

		this.node = node;

		this.resetGui();

		if (this.mc == null)
		{
			return;
		}

		Collection<IDialogButton> buttons = node.getButtons();

		Collection<IDialogContent> content = node.getContent();

		int baseBoxSize = 350;
		boolean resize = this.width - 40 > baseBoxSize;

		if (!resize)
		{
			baseBoxSize = this.width - 40;
		}

		this.topTextBox = new GuiTextBox(buttons.size(), resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 175, baseBoxSize, 80);
		this.bottomTextBox = new GuiTextBox(buttons.size() + 1, resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 85, baseBoxSize, 70);

		this.topTextBox.showBackdrop = true;
		this.topTextBox.bottomToTop = true;

		if (buttons.size() > 0 && this.textIndex + 1 >= content.size())
		{
			int index = 0;

			for (IDialogButton btn : buttons)
			{
				this.buttonList.add(new GuiDialogButton(index, (this.width / 2) - (baseBoxSize / 2), this.height - 85 + (index * 20), btn));

				index++;
			}
		}

		if (this.textIndex < content.size())
		{
			int i = 0;

			for (IDialogContent c : content)
			{
				if (i == this.textIndex)
				{
					if (this.textIndex + 1 >= content.size() && this.node.getButtons().size() > 0)
					{
						this.topTextBox.setText(c.getText());
					}
					else
					{
						this.bottomTextBox.setText(c.getText());
					}

					break;
				}

				i++;
			}
		}

		if (this.node.getSpeaker() != null)
		{
			this.fontRendererObj = Minecraft.getMinecraft().fontRendererObj;

			boolean topText = this.textIndex + 1 >= this.node.getContent().size() && this.node.getButtons().size() > 0;
			String name = I18n.format(this.node.getSpeaker().getResourcePath() + ".name");

			this.namePlate = new GuiTextBox(buttons.size() + 2, resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - (topText ? 122 + this.topTextBox.getTextHeight(this.fontRendererObj) : 107), this.fontRendererObj.getStringWidth(name + 10), 20);

			ITextComponent t = new TextComponentString(name);
			t.setStyle(new Style().setColor(TextFormatting.YELLOW).setItalic(true));

			this.namePlate.setText(t);
		}

		this.buttonList.add(this.topTextBox);
		this.buttonList.add(this.bottomTextBox);

		if (this.node.getSpeaker() != null)
		{
			this.buttonList.add(this.namePlate);
		}
	}

	private void nextAction()
	{
		if (!this.canApplyNextAction)
		{
			this.canApplyNextAction = true;

			return;
		}

		Collection<IDialogContent> content = this.node.getContent();

		if (content.size() <= 1)
		{
			for (IDialogAction action : this.node.getEndActions())
			{
				action.onAction(this);
			}

			return;
		}

		this.textIndex++;

		if (this.textIndex < content.size())
		{
			this.buildGui(this.node);
		}
		else
		{
			for (IDialogAction action : this.node.getEndActions())
			{
				action.onAction(this);
			}
		}
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

}
