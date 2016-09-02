package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class WearingItemsRule implements EntityEffectRule
{

	@Override
	public boolean isMet(Entity source)
	{
		return false;
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Wearing Full Set" };
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
