package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.EntityLivingBase;

import com.gildedgames.aether.common.entities.effects.AbilityRule;
import net.minecraft.util.EnumChatFormatting;

public class InCombatRule implements AbilityRule<EntityLivingBase>
{

	@Override
	public boolean isMet(EntityLivingBase source)
	{
		return false;
	}
	
	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "In Combat" };
	}
	
}
