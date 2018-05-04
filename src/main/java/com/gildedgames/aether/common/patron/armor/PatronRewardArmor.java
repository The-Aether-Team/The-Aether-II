package com.gildedgames.aether.common.patron.armor;

import com.gildedgames.aether.api.patron.IPatronReward;
import com.gildedgames.aether.api.patron.IPatronRewardRenderer;
import com.gildedgames.aether.api.patron.PatronDetails;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;

public class PatronRewardArmor implements IPatronReward
{
	private Supplier<String> armorTextureName;

	private Function<PatronDetails, Boolean> isUnlocked;

	private String rewardName;

	private ResourceLocation armorGloveTexture;

	private ResourceLocation icon;

	private IPatronRewardRenderer renderer;

	public PatronRewardArmor(String rewardName, ResourceLocation icon, Supplier<String> armorTextureName, ResourceLocation armorGloveTexture,
			Function<PatronDetails, Boolean> isUnlocked)
	{
		this.icon = icon;
		this.rewardName = rewardName;
		this.armorTextureName = armorTextureName;
		this.isUnlocked = isUnlocked;

		this.armorGloveTexture = armorGloveTexture;
	}

	public String getArmorTextureName()
	{
		return this.armorTextureName.get();
	}

	public ResourceLocation getArmorGloveTexture()
	{
		return this.armorGloveTexture;
	}

	@Override
	public ResourceLocation getRewardIcon()
	{
		return this.icon;
	}

	@Override
	public String getRewardName()
	{
		return this.rewardName;
	}

	@Override
	public boolean isUnlocked(PatronDetails details)
	{
		return this.isUnlocked.apply(details);
	}

	@Override
	public void onUse()
	{

	}

	@Override
	public void onRemove()
	{

	}

	@Override
	public IPatronRewardRenderer getPreviewRenderer()
	{
		if (this.renderer == null)
		{
			this.renderer = new PatronRewardArmorRenderer(this);
		}

		return this.renderer;
	}

}
