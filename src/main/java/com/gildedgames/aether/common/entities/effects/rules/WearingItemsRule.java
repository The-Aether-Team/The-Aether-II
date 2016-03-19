package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.EffectRule;

public class WearingItemsRule implements EffectRule
{

	@Override
	public boolean isMet(Entity source)
	{
		return false;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Wearing Full Set" };
	}

	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}
	
}
