package com.gildedgames.aether.common.entities.effects.rules;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.EffectRule;

public class DamagingMobRule implements EffectRule
{
	
	private Class<? extends Entity> mobClass;
	
	private String mobDisplayName;
	
	public DamagingMobRule(Class<? extends Entity> mobClass, String mobDisplayName)
	{
		this.mobClass = mobClass;
		this.mobDisplayName = mobDisplayName;
	}

	@Override
	public boolean isMet(Entity source)
	{
		return true;
	}

	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		boolean flag = !(event.entityLiving != null && this.mobClass.isAssignableFrom(event.entityLiving.getClass()));
		
		return flag;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Against " + this.mobDisplayName + "s" };
	}

}
