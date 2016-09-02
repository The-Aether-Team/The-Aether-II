package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.common.capabilities.entity.effects.EntityEffects;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class OutOfCombatRule implements EntityEffectRule
{

	private int minimumTime;

	public OutOfCombatRule(int minimumTime)
	{
		this.minimumTime = minimumTime;
	}

	@Override
	public boolean isMet(Entity source)
	{
		IEntityEffectsCapability effects = EntityEffects.get(source);

		return effects.getTicksSinceAttacked() > this.minimumTime;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Out of Combat" };
	}

	@Override
	public boolean blockLivingAttack(Entity source, float amount, Entity target)
	{
		return false;
	}

	@Override
	public boolean blockLivingHurt(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
