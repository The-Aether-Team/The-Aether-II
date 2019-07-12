package com.gildedgames.aether.api.patron;

import com.gildedgames.aether.api.net.data.UserFeatures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IPatronReward
{

	/**
	 * @return A 16x16 icon
	 */
	ResourceLocation getRewardIcon();

	String getUnlocalizedName();

	boolean isUnlocked(UserFeatures details);

	void onUse();

	void onRemove();

	@OnlyIn(Dist.CLIENT)
	IPatronRewardRenderer getPreviewRenderer();

}
