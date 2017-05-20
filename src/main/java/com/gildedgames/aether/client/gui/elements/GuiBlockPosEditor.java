package com.gildedgames.aether.client.gui.elements;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.Collection;

public class GuiBlockPosEditor extends Gui
{
	private final BlockPos pos;

	private final GuiTextField editX;

	private final GuiTextField editY;

	private final GuiTextField editZ;

	private final Collection<GuiTextField> fields;

	public GuiBlockPosEditor(BlockPos pos, int x, int y)
	{
		this.pos = pos;

		final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

		int width = 40;

		this.editX = new GuiTextField(0, fontRenderer, x, y, width, 20);
		this.editX.setText(String.valueOf(this.pos.getX()));

		this.editY = new GuiTextField(1, fontRenderer, x + 46, y, width, 20);
		this.editY.setText(String.valueOf(this.pos.getY()));

		this.editZ = new GuiTextField(2, fontRenderer, x + 92, y, width, 20);
		this.editZ.setText(String.valueOf(this.pos.getZ()));

		this.fields = Lists.newArrayList(this.editX, this.editY, this.editZ);
		this.fields.forEach((field) ->
		{
			field.setValidator(input -> input != null && input.length() > 0 && Ints.tryParse(input) != null);
		});
	}

	public void update()
	{
		this.fields.forEach(GuiTextField::updateCursorCounter);
	}

	public void draw(Minecraft mc, int mouseX, int mouseY)
	{
		this.fields.forEach(GuiTextField::drawTextBox);
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		this.fields.forEach((field) -> field.mouseClicked(mouseX, mouseY, mouseButton));
	}

	public void mouseReleased(int mouseX, int mouseY)
	{

	}

	public void keyPressed(char typedChar, int keyCode)
	{
		boolean shift = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);

		if (keyCode == 200)
		{
			this.incrementActiveField(shift ? 5 : 1);
		}
		else if (keyCode == 208)
		{
			this.incrementActiveField(shift ? -5 : -1);
		}

		this.fields.forEach((field) -> field.textboxKeyTyped(typedChar, keyCode));
	}

	private void incrementActiveField(int amount)
	{
		this.fields.stream().filter(GuiTextField::isFocused).findFirst().ifPresent((field) ->
		{
			Integer value = Integer.parseInt(field.getText());
			value += amount;

			field.setText(String.valueOf(value));
		});
	}

	public BlockPos getModifiedPos()
	{
		int x = Integer.parseInt(this.editX.getText());
		int y = Integer.parseInt(this.editY.getText());
		int z = Integer.parseInt(this.editZ.getText());

		return new BlockPos(x, y, z);
	}
}
