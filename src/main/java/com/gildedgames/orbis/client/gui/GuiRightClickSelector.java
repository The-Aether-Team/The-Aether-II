package com.gildedgames.orbis.client.gui;

import com.gildedgames.orbis.client.gui.util.GuiDropdownList;
import com.gildedgames.orbis.client.gui.util.GuiFrame;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis_core.world_objects.WorldShape;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class GuiRightClickSelector extends GuiFrame
{
	private final WorldShape region;

	public GuiRightClickSelector(final WorldShape region)
	{
		super(Dim2D.flush());

		this.region = region;
	}

	@Override
	public void init()
	{
		this.addChild(new GuiDropdownList(Pos2D.flush(this.width / 2, this.height / 2),
				GuiRightClickElements.delete(this.region),
				GuiRightClickElements.copy(this.region),
				GuiRightClickElements.close()));
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (Minecraft.getMinecraft().currentScreen == this)
		{
			Minecraft.getMinecraft().displayGuiScreen(null);
			GuiRightClickBlueprint.lastCloseTime = System.currentTimeMillis();
		}
	}
}
