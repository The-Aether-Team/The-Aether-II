package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class DamagingMobRule implements EntityEffectRule
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
	public boolean blockLivingAttack(Entity source, LivingAttackEvent event)
	{
		return !(event.getEntityLiving() != null && this.mobClass.isAssignableFrom(event.getEntityLiving().getClass()));
	}

	@Override
	public boolean blockLivingHurt(Entity source, LivingHurtEvent event)
	{
		return !(event.getEntityLiving() != null && this.mobClass.isAssignableFrom(event.getEntityLiving().getClass()));
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Against " + this.mobDisplayName + "s" };
	}

}
