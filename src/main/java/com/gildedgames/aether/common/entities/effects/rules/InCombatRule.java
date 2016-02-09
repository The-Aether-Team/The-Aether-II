package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.EntityLivingBase;

import com.gildedgames.aether.common.entities.effects.AbilityRule;
import com.mojang.realmsclient.gui.ChatFormatting;

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
		return new String[] { ChatFormatting.GRAY + "" + ChatFormatting.ITALIC + "In Combat" };
	}
	
}
