package com.gildedgames.aether.common.entities.effects.rules;

import com.gildedgames.aether.entities.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
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
		return new String[] { EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Wearing Full Set" };
	}

	@Override
	public boolean blockLivingAttackAbility(Entity source, LivingHurtEvent event)
	{
		return false;
	}

}
