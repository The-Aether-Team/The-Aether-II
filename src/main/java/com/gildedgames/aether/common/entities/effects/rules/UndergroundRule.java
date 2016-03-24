package com.gildedgames.aether.common.entities.effects.rules;

import com.gildedgames.aether.common.entities.effects.EffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class UndergroundRule implements EffectRule
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

	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
