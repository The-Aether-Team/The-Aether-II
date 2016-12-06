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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.Sys;

import java.io.IOException;
import java.util.Collection;

public class GuiDialogController extends GuiContainer implements IDialogController
{

	private static final ResourceLocation NEXT_ARROW = AetherCore.getResource("textures/gui/conversation/next_arrow.png");

	private final EntityPlayer player;

	private DialogTree scene;

	private IDialogNode node;

	private GuiTextBox textBox;

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

		if ((this.textIndex < this.node.getContent().size() && this.node.getButtons().isEmpty()) || this.node.getButtons().isEmpty())
		{
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

			GlStateManager.pushMatrix();

			double anim = this.nextArrowAnim;

			if (this.nextArrowAnim < 500.0)
			{
				GlStateManager.translate(0, anim / 500.0, 0);
			}
			else if (this.nextArrowAnim >= 500.0)
			{
				GlStateManager.translate(0, -((anim - 500.0) / 500.0), 0);
			}

			Minecraft.getMinecraft().renderEngine.bindTexture(NEXT_ARROW);
			Gui.drawModalRectWithCustomSizedTexture(this.textBox.xPosition + this.textBox.width - 20, this.textBox.yPosition + this.textBox.height - 20, 0, 0, 13, 12, 13, 12);

			GlStateManager.popMatrix();
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		/*GlStateManager.pushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(PORTRAIT);

		double scale = 0.2;

		double scaledWidth = 780 * scale;
		double scaledHeight = 1172 * scale;

		GlStateManager.translate((this.width / 2) - (scaledWidth / 2) + 40, this.height - scaledHeight, 0);
		GlStateManager.scale(scale, scale, scale);

		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 780, 1172, 780, 1172);
		GlStateManager.popMatrix();*/

		if (this.npc == null)
		{
			this.npc = new EntityEdison(this.mc.theWorld);
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, 300F);
		GuiInventory.drawEntityOnScreen((this.width / 2) + 40, this.height, 120, -mouseX + (this.width / 2) + 40, (-mouseY / 10.0F), this.npc);
		GlStateManager.popMatrix();
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

		Collection<IDialogButton> buttons = node.getButtons();

		Collection<ITextComponent> text = node.getContent();

		int baseBoxSize = 350;

		if (this.width - 40 > baseBoxSize)
		{
			this.textBox = new GuiTextBox(buttons.size(), (this.width / 2) - (baseBoxSize / 2), this.height - 110, baseBoxSize, 80);
		}
		else
		{
			baseBoxSize = this.width - 40;
			this.textBox = new GuiTextBox(buttons.size(), 20, this.height - 110, baseBoxSize, 80);
		}

		if (buttons.size() > 0 && this.textIndex + 1 >= text.size())
		{
			int index = 0;

			for (IDialogButton btn : buttons)
			{
				this.buttonList.add(new GuiDialogButton(index, (this.width / 2) - (baseBoxSize / 2), this.height - 200 + (index * 20), btn));

				index++;
			}
		}

		if (this.textIndex < text.size())
		{
			int i = 0;

			for (ITextComponent t : text)
			{
				if (i == this.textIndex)
				{
					this.textBox.setText(t);
					break;
				}

				i++;
			}
		}

		this.buttonList.add(this.textBox);
	}

	private void nextAction()
	{
		if (!this.canApplyNextAction)
		{
			this.canApplyNextAction = true;

			return;
		}

		Collection<ITextComponent> text = this.node.getContent();

		if (text.size() <= 1)
		{
			for (IDialogAction action : this.node.getEndActions())
			{
				action.onAction(this);
			}

			return;
		}

		this.textIndex++;

		if (this.textIndex < text.size())
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
