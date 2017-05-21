package com.gildedgames.aether.client.gui;

import com.gildedgames.aether.client.gui.elements.GuiBlockPosEditor;
import com.gildedgames.aether.common.entities.tiles.builder.TileEntityStructureBuilder;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSaveStructure;
import com.gildedgames.aether.common.network.packets.PacketUpdateStructure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiStructureBuilder extends GuiScreen
{
	private final World world;

	private final BlockPos pos;

	private TileEntityStructureBuilder.Data data;

	private GuiBlockPosEditor editOrigin;

	private GuiBlockPosEditor editSize;

	private GuiTextField editName;

	private GuiButton updateButton, cancelButton, saveButton;

	public GuiStructureBuilder(EntityPlayer player, BlockPos pos, TileEntityStructureBuilder.Data data)
	{
		this.world = player.getEntityWorld();
		this.pos = pos;
		this.data = data;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		if (this.editOrigin != null)
		{
			this.editOrigin.update();
		}

		if (this.editSize != null)
		{
			this.editSize.update();
		}

		this.updateButtonStates();
	}

	private void updateButtonStates()
	{
		this.saveButton.enabled = this.editName.getText().length() > 0;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		super.drawScreen(mouseX, mouseY, partialTicks);

		this.editOrigin.draw(this.mc, mouseX, mouseY);
		this.editSize.draw(this.mc, mouseX, mouseY);

		this.editName.drawTextBox();

		this.drawString(this.mc.fontRenderer, "Name", 20, 32, 0xFFFFFF);
		this.drawString(this.mc.fontRenderer, "Relative Origin", 20, 76, 0xFFFFFF);
		this.drawString(this.mc.fontRenderer, "Size", 20, 120, 0xFFFFFF);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);

		this.editOrigin.keyPressed(typedChar, keyCode);
		this.editSize.keyPressed(typedChar, keyCode);

		this.editName.textboxKeyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		this.editOrigin.mouseClicked(mouseX, mouseY, mouseButton);
		this.editSize.mouseClicked(mouseX, mouseY, mouseButton);
		this.editName.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);

		this.editName = new GuiTextField(0, this.mc.fontRenderer, 20, 46, 200, 20);
		this.editOrigin = new GuiBlockPosEditor(this.data.offset, 20, 90, true);
		this.editSize = new GuiBlockPosEditor(this.data.size, 20, 134, false);

		this.updateButton = new GuiButton(10, this.width - 110, this.height - 30, 100, 20, "Update Changes");
		this.cancelButton = new GuiButton(11, this.width - 174, this.height - 30, 60, 20, "Cancel");
		this.saveButton = new GuiButton(12, 226, 46, 60, 20, "Save file");

		this.buttonList.add(this.updateButton);
		this.buttonList.add(this.cancelButton);
		this.buttonList.add(this.saveButton);

		this.editName.setText(this.data.name);

		this.updateButtonStates();
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id == this.cancelButton.id)
		{
			Minecraft.getMinecraft().displayGuiScreen(null);

			return;
		}

		if (button.id == this.updateButton.id || button.id == this.saveButton.id)
		{
			this.data.name = this.editName.getText();
			this.data.size = this.editSize.getModifiedPos();
			this.data.offset = this.editOrigin.getModifiedPos();

			NetworkingAether.sendPacketToServer(new PacketUpdateStructure(this.pos, this.data));

			if (button.id == this.saveButton.id)
			{
				NetworkingAether.sendPacketToServer(new PacketSaveStructure(this.pos));
			}

			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
