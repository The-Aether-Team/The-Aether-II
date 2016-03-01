package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;

import com.gildedgames.aether.common.entities.effects.AbilityRule;
import com.mojang.realmsclient.gui.ChatFormatting;

public class WearingItemsRule implements AbilityRule<Entity>
{

	@Override
	public boolean isMet(Entity source)
	{
		return false;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { ChatFormatting.GRAY + "" + ChatFormatting.ITALIC + "Wearing Full Set" };
	}

}
