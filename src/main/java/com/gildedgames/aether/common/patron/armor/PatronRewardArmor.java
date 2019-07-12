package com.gildedgames.aether.common.patron.armor;

import com.gildedgames.aether.api.net.data.UserFeatures;
import com.gildedgames.aether.api.patron.IPatronReward;
import com.gildedgames.aether.api.patron.IPatronRewardRenderer;
import com.gildedgames.aether.client.renderer.PatronRewardArmorRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import java.util.function.Function;

public class PatronRewardArmor implements IPatronReward
{
	private final String armorTextureName;

	private final Function<UserFeatures, Boolean> isUnlocked;

	private final String unlocalizedName;

	private final ResourceLocation armorGloveTexture;

	private final ResourceLocation armorGloveTextureSlim;

	private final ResourceLocation icon;

	@OnlyIn(Dist.CLIENT)
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
	@OnlyIn(Dist.CLIENT)
	public IPatronRewardRenderer getPreviewRenderer()
	{
		if (this.renderer == null)
		{
			this.renderer = new PatronRewardArmorRenderer(this);
		}

		return this.renderer;
	}

}
