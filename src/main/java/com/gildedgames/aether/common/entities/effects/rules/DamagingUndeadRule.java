package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.EffectRule;

public class DamagingUndeadRule implements EffectRule
{
	
	public DamagingUndeadRule()
	{
		
	}

	@Override
	public boolean isMet(Entity source)
	{
		return true;
	}

	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return !(event.entityLiving != null && event.entityLiving.isEntityUndead());
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Against Undead" };
	}

}
