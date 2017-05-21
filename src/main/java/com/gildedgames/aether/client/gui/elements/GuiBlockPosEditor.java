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
	private final BlockPos original;

	private final GuiTextField editX;

	private final GuiTextField editY;

	private final GuiTextField editZ;

	private final Collection<GuiTextField> fields;

	private final boolean allowNegative;

	public GuiBlockPosEditor(BlockPos original, int x, int y, boolean allowNegative)
	{
		this.original = original;
		this.allowNegative = allowNegative;

		final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

		int width = 40;

		this.editX = new GuiTextField(0, fontRenderer, x, y, width, 20);
		this.editX.setTextColor(0xff9999);
		this.editX.setText(String.valueOf(this.original.getX()));

		this.editY = new GuiTextField(1, fontRenderer, x + 46, y, width, 20);
		this.editY.setTextColor(0x99ff99);
		this.editY.setText(String.valueOf(this.original.getY()));

		this.editZ = new GuiTextField(2, fontRenderer, x + 92, y, width, 20);
		this.editZ.setTextColor(0x9999ff);
		this.editZ.setText(String.valueOf(this.original.getZ()));

		this.fields = Lists.newArrayList(this.editX, this.editY, this.editZ);
		this.fields.forEach((field) ->
				field.setValidator(input ->
				{
					if (input == null)
					{
						return false;
					}

					if (input.length() == 0 || (this.allowNegative && input.charAt(0) == '-'))
					{
						return true;
					}

					Integer value = Ints.tryParse(input);

					return value != null && (this.allowNegative || value > 0);
				}));
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
		this.fields.forEach((field) -> {
			boolean wasFocused = field.isFocused();

			field.mouseClicked(mouseX, mouseY, mouseButton);

			if (wasFocused != field.isFocused())
			{
				if (Ints.tryParse(field.getText()) == null)
				{
					if (field == this.editX)
					{
						field.setText(String.valueOf(this.original.getX()));
					}
					else if (field == this.editY)
					{
						field.setText(String.valueOf(this.original.getY()));
					}
					else if (field == this.editZ)
					{
						field.setText(String.valueOf(this.original.getZ()));
					}
				}

				field.setText(String.valueOf(Integer.valueOf(field.getText())));
			}
		});
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
			Integer value = Ints.tryParse(field.getText());

			if (value == null)
			{
				return;
			}

			value += amount;

			if (!this.allowNegative && value < 0)
			{
				return;
			}

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
