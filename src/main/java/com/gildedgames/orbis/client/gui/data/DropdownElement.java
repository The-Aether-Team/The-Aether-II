package com.gildedgames.orbis.client.gui.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

public class DropdownElement implements IDropdownElement
{
	private final ITextComponent text;

	public DropdownElement(final ITextComponent text)
	{
		this.text = text;
	}

	@Override
	public ITextComponent text()
	{
		return this.text;
	}

	@Override
	public void onClick(final EntityPlayer player)
	{

	}
}
