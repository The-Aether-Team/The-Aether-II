package com.gildedgames.orbis.client.gui.data;

import com.gildedgames.orbis.client.gui.util.GuiDropdownList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

public interface IDropdownElement
{

	ITextComponent text();

	void onClick(GuiDropdownList list, EntityPlayer player);

}
