package com.gildedgames.aether.api.patron;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IPatronRewardRenderer
{
	@SideOnly(Side.CLIENT)
	void renderInit();

	@SideOnly(Side.CLIENT)
	void renderPreview(int posX, int posY);
}
