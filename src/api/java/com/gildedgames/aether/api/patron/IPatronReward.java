package com.gildedgames.aether.api.patron;

import com.gildedgames.aether.api.net.data.UserFeatures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	@SideOnly(Side.CLIENT)
	IPatronRewardRenderer getPreviewRenderer();

}
