package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class DamagingUndeadRule implements EntityEffectRule
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
	public boolean blockLivingAttack(Entity source, float amount, Entity target)
	{
		return !(target instanceof EntityLivingBase && ((EntityLivingBase)target).isEntityUndead());
	}

	@Override
	public boolean blockLivingHurt(Entity source, LivingHurtEvent event)
	{
		return !(event.getEntityLiving() != null && event.getEntityLiving().isEntityUndead());
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Against Undead" };
	}

}
