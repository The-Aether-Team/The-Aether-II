package com.gildedgames.aether.client.gui.dialog;

import net.minecraft.client.gui.GuiScreen;

public class GuiScreenCustom extends GuiScreen
{

	public boolean doesGuiPauseGame()
	{
		return false;
	}

	public void updateScreen()
	{
		super.updateScreen();

		if (!this.mc.player.isEntityAlive() || this.mc.player.isDead)
		{
			this.mc.player.closeScreen();
		}
	}

}
