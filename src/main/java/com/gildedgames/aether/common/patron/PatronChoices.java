package com.gildedgames.aether.common.patron;

import com.gildedgames.aether.api.patron.PatronDetails;
import com.gildedgames.aether.api.patron.PatronRewardRegistry;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.nbt.NBTTagCompound;

public class PatronChoices implements NBT
{
	private PatronRewardArmor armorChoice;

	private PatronDetails details;

	private PatronRewardRegistry registry;

	public PatronChoices(PatronDetails details, PatronRewardRegistry registry)
	{
		this.details = details;
		this.registry = registry;
	}

	public PatronRewardArmor getArmorChoice()
	{
		return this.armorChoice;
	}

	public void setArmorChoice(PatronRewardArmor armorChoice)
	{
		if (armorChoice == null || armorChoice.isUnlocked(this.details))
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
