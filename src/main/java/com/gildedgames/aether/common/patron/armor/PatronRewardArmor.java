package com.gildedgames.aether.common.patron.armor;

import com.gildedgames.aether.api.net.data.UserFeatures;
import com.gildedgames.aether.api.patron.IPatronReward;
import com.gildedgames.aether.api.patron.IPatronRewardRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class PatronRewardArmor implements IPatronReward
{
	private String armorTextureName;

	private Function<UserFeatures, Boolean> isUnlocked;

	private String unlocalizedName;

	private ResourceLocation armorGloveTexture, armorGloveTextureSlim;

	private ResourceLocation icon;

	private IPatronRewardRenderer renderer;

	public PatronRewardArmor(String unlocalizedName, ResourceLocation icon, String armorTextureName, ResourceLocation armorGloveTexture,
			ResourceLocation armorGloveTextureSlim,
			Function<UserFeatures, Boolean> isUnlocked)
	{
		this.icon = icon;
		this.unlocalizedName = unlocalizedName;
		this.armorTextureName = armorTextureName;
		this.isUnlocked = isUnlocked;

		this.armorGloveTexture = armorGloveTexture;
		this.armorGloveTextureSlim = armorGloveTextureSlim;
	}

	public String getArmorTextureName()
	{
		return this.armorTextureName;
	}

	public ResourceLocation getArmorGloveTexture(boolean slim)
	{
		return slim ? this.armorGloveTextureSlim : this.armorGloveTexture;
	}

	@Override
	public ResourceLocation getRewardIcon()
	{
		return this.icon;
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}

	@Override
	public boolean isUnlocked(UserFeatures details)
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
