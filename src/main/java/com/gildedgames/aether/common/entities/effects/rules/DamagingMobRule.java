package com.gildedgames.aether.common.entities.effects.rules;

import com.gildedgames.aether.entities.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
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
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return !(event.entityLiving != null && this.mobClass.isAssignableFrom(event.entityLiving.getClass()));
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Against " + this.mobDisplayName + "s" };
	}

}
