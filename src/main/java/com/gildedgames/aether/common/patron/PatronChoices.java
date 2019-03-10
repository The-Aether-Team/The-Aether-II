package com.gildedgames.aether.common.patron;

import com.gildedgames.aether.api.patron.PatronRewardRegistry;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPatronRewards;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.nbt.NBTTagCompound;

public class PatronChoices implements NBT
{
	private PatronRewardArmor armorChoice;

	private final PlayerPatronRewards parent;

	private final PatronRewardRegistry registry;

	public PatronChoices(PlayerPatronRewards parent, PatronRewardRegistry registry)
	{
		this.parent = parent;
		this.registry = registry;
	}

	public PatronRewardArmor getArmorChoice()
	{
		return this.armorChoice;
	}

	public void setArmorChoice(PatronRewardArmor armorChoice)
	{
		if (armorChoice == null || armorChoice.isUnlocked(this.parent.getFeatures()))
		{
			this.armorChoice = armorChoice;
		}
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setInteger("armorChoice", this.registry.get(this.armorChoice));
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.armorChoice = this.registry.get(tag.getInteger("armorChoice"));
	}
}
