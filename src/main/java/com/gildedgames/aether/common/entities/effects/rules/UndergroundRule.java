package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;

import com.gildedgames.aether.common.entities.effects.AbilityRule;
import net.minecraft.util.EnumChatFormatting;

public class UndergroundRule implements AbilityRule<Entity>
{

	@Override
	public boolean isMet(Entity source)
	{
		return false;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Underground" };
	}
	
}
