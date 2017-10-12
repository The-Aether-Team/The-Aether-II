package com.gildedgames.orbis.client.gui.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

public interface IDropdownElement
{

	ITextComponent text();

	void onClick(EntityPlayer player);

}
