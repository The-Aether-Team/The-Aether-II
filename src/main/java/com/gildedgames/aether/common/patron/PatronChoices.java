package com.gildedgames.aether.common.patron;

import com.gildedgames.aether.api.patron.PatronRewardRegistry;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPatronRewardModule;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.nbt.CompoundNBT;

public class PatronChoices implements NBT
{
	private PatronRewardArmor armorChoice;

	private final PlayerPatronRewardModule parent;

	private final PatronRewardRegistry registry;

	public PatronChoices(PlayerPatronRewardModule parent, PatronRewardRegistry registry)
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
	public void write(CompoundNBT tag)
	{
		tag.putInt("armorChoice", this.registry.get(this.armorChoice));
	}

	@Override
	public void read(CompoundNBT tag)
	{
		this.armorChoice = this.registry.get(tag.getInt("armorChoice"));
	}
}
