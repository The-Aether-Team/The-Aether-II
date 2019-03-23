package com.gildedgames.aether.api.patron;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IPatronRewardRenderer
{
	@OnlyIn(Dist.CLIENT)
	void renderInit();

	@OnlyIn(Dist.CLIENT)
	void renderPreview(int posX, int posY);
}
